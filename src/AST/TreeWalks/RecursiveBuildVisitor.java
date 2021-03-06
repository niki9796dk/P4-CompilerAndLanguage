package AST.TreeWalks;

import AST.Nodes.AbstractNodes.Nodes.AbstractNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNode;
import AST.Nodes.NodeClasses.NamedNodes.ParamsNode;
import CompilerExceptions.ScopeExceptions.NoSuchBlockDeclaredException;
import SemanticAnalysis.Datastructures.HashSetStack;
import SemanticAnalysis.Datastructures.SetStack;
import CompilerExceptions.SemanticExceptions.BuildRecursionException;
import CompilerExceptions.TypeExceptions.ShouldNotHappenException;
import CompilerExceptions.UnexpectedNodeException;
import SymbolTableImplementation.SymbolTable;
import TypeChecker.TypeSystem;

/**
 * A recursive build visitor, which will **follow** all build statements to verify that no build recursions happen.
 */
public class RecursiveBuildVisitor extends ScopeTracker {

    private SetStack<String> buildStack;
    private SetStack<String> procedureStack;

    public RecursiveBuildVisitor(SetStack<String> buildStack, SymbolTable symbolTable) {
        super(symbolTable);
        this.buildStack = buildStack;
        this.procedureStack = new HashSetStack<>();
        this.typeSystem = new TypeSystem(symbolTable);
    }

    /**
     * @param printLevel the level, used to decide how many indents there should be in the print statement.
     * @param node       The node which is being visited.
     */
    @Override
    public void pre(int printLevel, AbstractNode node) {
        // Update scope counter
        super.pre(printLevel, node);

        // Execute visitor
        NamedNode namedNode = (NamedNode) node;

        switch (namedNode.getNodeEnum()) {
            // No action enums
            case BLOCK:
            case BLUEPRINT:
            case CHANNEL_DECLARATIONS:
            case ROOT:
            case GROUP:
            case CHAIN:
            case PARAMS:
            case SELECTOR:
            case DRAW:
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
            case PROCEDURE:
                break;

            case PROCEDURE_CALL:
                if (!procedureStack.push(node.toString() + getParamAsString(node)))
                {
                    throw new BuildRecursionException(node, "Recursive procedures! - " + node);
                }
                break;
            case BUILD:
                this.handleBuildPre(node);
                break;

            default:
                throw new UnexpectedNodeException(namedNode);
        }
    }

    /**
     * @param printLevel   the level, used to decide how many indents there should be in the print statement.
     * @param abstractNode The node which is being visited.
     */
    @Override
    @SuppressWarnings("Duplicates")
    public void post(int printLevel, AbstractNode abstractNode) {
        // Update scope counter
        super.post(printLevel, abstractNode);

        // Execute visitor
        NamedNode node = (NamedNode) abstractNode;

        switch (node.getNodeEnum()) {
            // No action enums
            case ROOT:
            case GROUP:
            case CHAIN:
            case PROCEDURE_CALL:
            case PARAMS:
            case SELECTOR:
            case DRAW:
            case SIZE:
            case ASSIGN:
            case BLOCK:
            case BLUEPRINT:
            case CHANNEL_DECLARATIONS:
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

            case PROCEDURE:
                procedureStack.pop();
                break;
            case BUILD:
                // Once done, pop the newly added build
                this.handleBuildPost(node);
                break;

            default:
                throw new UnexpectedNodeException(node);
        }
    }

    /**
     * Method to handle a BuildNode.
     * Will check if whatever it is we are building is a block, and then store the call in a call stack
     * and if the same call is still within the stack (no pop) it means there is an recursion, and an exception is thrown.
     *
     * @param node the BuildNode to follow.
     */
    private void handleBuildPre(AbstractNode node) {
        String nodeSubType;

        try {
            nodeSubType = this.typeSystem.getSubTypeOfNode(node, this.currentBlockScope, this.currentSubScope);
        } catch (ShouldNotHappenException e) {
            throw new NoSuchBlockDeclaredException(node, e);
        }

        boolean isSource = this.typeSystem.isPredefinedSource(nodeSubType);
        boolean isOperation = this.typeSystem.isPredefinedOperation(nodeSubType);

        if (isSource || isOperation) {
            // Do nothing
            return;
        }

        // Add the build to the build stack.
        boolean failure = !this.buildStack.push(this.convertBuildNodeToString(node));

        if (failure) {
            throw new BuildRecursionException(node, "Recursive block building! - " + node);
        }
    }

    /**
     * Method to handle a BuildNode.
     * Will check if whatever it is we are building is a block, and then pop the call in a call stack
     *
     * @param node the BuildNode to follow.
     */
    private void handleBuildPost(AbstractNode node) {
        String nodeSubType = this.typeSystem.getSubTypeOfNode(node, this.currentBlockScope, this.currentSubScope);

        boolean isSource = this.typeSystem.isPredefinedSource(nodeSubType);
        boolean isOperation = this.typeSystem.isPredefinedOperation(nodeSubType);

        if (isSource || isOperation) {
            // Do nothing
            return;
        }

        this.buildStack.pop();
    }

    /**
     * Convert a build node into a string representation of it's ID and the subtype of it's params.
     *
     * @param buildNode The BuildNode to convert.
     * @return A string representation of build call.
     */
    public String convertBuildNodeToString(AbstractNode buildNode) {
        String buildTypeSubType = this.typeSystem.getSubTypeOfNode(buildNode, this.currentBlockScope, this.currentSubScope);
        String paramAsString = this.getParamAsString(buildNode);

        return buildTypeSubType + ": " + paramAsString;
    }

    /**
     * Helper method for converting the build node into a string.
     * Will convert the parameter part of the string representation, by loop all parameters and storing their subtypes.
     *
     * @param buildNode The BuildNode to convert.
     * @return A string representation of the parameter part of the BuildNode.
     */
    private String getParamAsString(AbstractNode buildNode) {
        ParamsNode paramsNode = buildNode.findFirstChildOfClass(ParamsNode.class);

        if (paramsNode == null) {
            return "NoParams";

        } else {
            StringBuilder stringBuilder = new StringBuilder();

            for (AbstractNode child = paramsNode.getChild(); child != null; child = child.getSib()) {
                stringBuilder.append(this.convertSingleParamToString(child));
                stringBuilder.append(", ");
            }

            int delEnd = stringBuilder.length();
            int delStart = delEnd - 2;
            stringBuilder.delete(delStart, delEnd);

            return stringBuilder.toString();
        }
    }

    /**
     * Helper method for converting the build node into a string.
     * Will convert a single parameter into it's string representation.
     *
     * @param param The single parameter node to convert.
     * @return A string representation of a single parameter.
     */
    private String convertSingleParamToString(AbstractNode param) {
        return this.typeSystem.getSubTypeOfNode(param, this.currentBlockScope, this.currentSubScope);
    }

}
