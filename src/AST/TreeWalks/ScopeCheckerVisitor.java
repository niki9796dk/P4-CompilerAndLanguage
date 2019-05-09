package AST.TreeWalks;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNode;
import AST.Nodes.NodeClasses.NamedNodes.ProcedureCallNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.SelectorNode;
import ScopeChecker.Exceptions.IllegalProcedureCallScopeException;
import ScopeChecker.Exceptions.NoSuchBlockDeclaredException;
import ScopeChecker.Exceptions.NoSuchChannelDeclaredException;
import ScopeChecker.Exceptions.NoSuchVariableDeclaredException;
import AST.TreeWalks.Exceptions.UnexpectedNodeException;
import AST.Visitor;
import SymbolTableImplementation.*;

/**
 * The scope checker visitor, used for checking everything in the scope checking phase of a the compiler.
 */
public class ScopeCheckerVisitor implements Visitor {

    private SymbolTableInterface symbolTableInterface;
    private BlockScope currentBlockScope;
    private Scope currentSubScope;

    public ScopeCheckerVisitor(SymbolTableInterface symbolTableInterface) {
        this.symbolTableInterface = symbolTableInterface;
        this.currentBlockScope = null;
        this.currentSubScope = null;
    }

    public BlockScope getCurrentBlockScope() {
        return currentBlockScope;
    }

    public Scope getCurrentSubScope() {
        return currentSubScope;
    }

    /**
     * The preorder walk for the visitor.
     * @param printLevel the level, used to decide how many indents there should be in the print statement.
     * @param abstractNode The node which is being visited.
     */
    @Override
    public void pre(int printLevel, AbstractNode abstractNode) {
        NamedNode node = (NamedNode) abstractNode;
        NamedNode childNode = (NamedNode) node.getChild();

        // If needed, typecast
        String id = (node instanceof NamedIdNode) ? ((NamedIdNode) node).getId() : "?no id? " + node.toString();

        String childId = (childNode instanceof NamedIdNode) ? ((NamedIdNode) childNode).getId() : "?no id? child of " + node.toString();

        switch (node.getNodeEnum()) {
            case ROOT:
            case GROUP:
            case CHAIN:
            case PARAMS:
            case SIZE:
            case ASSIGN:
            case CHANNEL_IN_MY:
            case CHANNEL_OUT_MY:
            case CHANNEL_IN_TYPE:
            case CHANNEL_OUT_TYPE:
            case SIZE_TYPE:
            case BLOCK_TYPE:
            case SOURCE_TYPE:
            case BLUEPRINT_TYPE:
            case OPERATION_TYPE:
                break;
            case DRAW:
            case BUILD:
                if (this.isNotADefinedBuildableElement(id) && this.isNotLocalBlueprintVariable(id)) {
                    throw new NoSuchBlockDeclaredException(node.toString());
                }

                break;

            case BLOCK:
                this.currentBlockScope = this.symbolTableInterface.getBlockScope(id);
                break;

            case PROCEDURE:
                this.currentSubScope = this.currentBlockScope.getProcedureScope(id);
                break;

            case PROCEDURE_CALL:
                SelectorNode childSelector = node.findFirstChildOfClass(SelectorNode.class);

                Scope scope = this.currentBlockScope.getProcedureScope(childSelector.getId());
                if (scope == null) {
                    throw new IllegalProcedureCallScopeException("No such procedure: " + childSelector.getId());
                }
                break;

            case BLUEPRINT:
                this.currentSubScope = this.currentBlockScope.getBlueprintScope();
                break;

            case CHANNEL_DECLARATIONS:
                this.currentSubScope = this.currentBlockScope.getChannelDeclarationScope();
                break;

            case SELECTOR:
                boolean ignoreSelector = node.getParent() instanceof ProcedureCallNode;

                if ("this".equals(id)) {
                    this.verifyChannelVariable(childId);

                } else if (ignoreSelector) {
                    // Do nothing.

                } else if (!(node.getParent() instanceof SelectorNode)) {
                    try {
                        this.verifyCurrentScopeVariable(id);
                    } catch (NoSuchVariableDeclaredException e) {
                        this.verifyChannelVariable(id);
                    }
                }
                break;
            default:
                throw new UnexpectedNodeException(node);
        }
    }

    /**
     * The post order walk for the visitor
     * @param printLevel the level, used to decide how many indents there should be in the print statement.
     * @param abstractNode The node which is being visited.
     */
    @Override
    public void post(int printLevel, AbstractNode abstractNode) {
        NamedNode node = (NamedNode) abstractNode;

        switch (node.getNodeEnum()) {
            case ROOT:
            case GROUP:
            case CHAIN:
            case PROCEDURE_CALL:
            case PARAMS:
            case SELECTOR:
            case DRAW:
            case BUILD:
            case SIZE:
            case BLOCK:
            case BLUEPRINT:
            case PROCEDURE:
            case CHANNEL_IN_MY:
            case CHANNEL_OUT_MY:
            case CHANNEL_IN_TYPE:
            case CHANNEL_OUT_TYPE:
            case SIZE_TYPE:
            case BLOCK_TYPE:
            case SOURCE_TYPE:
            case BLUEPRINT_TYPE:
            case OPERATION_TYPE:
            case CHANNEL_DECLARATIONS:
            case ASSIGN:
                break;

            default:
                throw new UnexpectedNodeException(node);
        }
    }

    /**
     * Checks if a build id is not a buildable element such as a Source, Operation or Block.
     * @param buildId The id to check.
     * @return false if the element is buildable or true if it's not.
     */
    private boolean isNotADefinedBuildableElement(String buildId) {
        boolean isADefinedBlock = this.symbolTableInterface.getBlockScope(buildId) != null;
        boolean isPredefinedOperation = this.symbolTableInterface.isPredefinedOperation(buildId);
        boolean isPredefinedSource = this.symbolTableInterface.isPredefinedSource(buildId);

        return !(isADefinedBlock || isPredefinedOperation || isPredefinedSource);
    }

    /**
     * Check if a build id is not a local blueprint variable.
     * @param buildId The build id to check.
     * @return false if it's not a local blueprint variable, and true if it is.
     */
    private boolean isNotLocalBlueprintVariable(String buildId) {
        VariableEntry localVariable = this.currentSubScope.getVariable(buildId);

        // Check if the build ID is a local variable
        if (localVariable != null) {
            // If it is, check that it's not of blueprint type.
            return localVariable.getSuperType() != NodeEnum.BLUEPRINT_TYPE;

        } else {
            return true;
        }
    }

    /**
     * Verifies that an id is a channel variable, and throws an exception if it's not.
     * @param id the channel id to check.
     * @throws NoSuchVariableDeclaredException if the id is not a channel.
     */
    private void verifyChannelVariable(String id) {
        VariableEntry variable = this.currentBlockScope.getChannelDeclarationScope().getVariable(id);

        checkIfNull(variable, "ChannelDeclarations", id);
    }

    /**
     * Verify that an id is a variable in the current local scope, and throws an exception if it's not.
     * @param id The variable id to check
     * @throws NoSuchVariableDeclaredException if the id is not a local variable.
     */
    private void verifyCurrentScopeVariable(String id) {
        VariableEntry variable = this.currentSubScope.getVariable(id);

        checkIfNull(variable, this.currentBlockScope.getId(), id);
    }

    /**
     * Helper function used to simplify checking if a variable entry is null, and throw an exception if it is.
     * @param variable The variable entry to compare.
     * @param subScope The subscope the variable entry was extracted from - Used for error messages.
     * @param id The variable id the variable entry was extracted with - Used for error messages .
     */
    private void checkIfNull(VariableEntry variable, String subScope, String id) {
        if (variable == null) {
            throw new NoSuchVariableDeclaredException("In scope: " + this.currentBlockScope.getId() + ">" + subScope + ", no such variable: " + id);
        }
    }
}
