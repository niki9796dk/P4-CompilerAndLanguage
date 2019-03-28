package AST.TreeWalks;

import AST.Nodes.AbstractNodes.Nodes.AbstractNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.DrawNode;
import AST.Nodes.NodeClasses.NamedNodes.ProcedureCallNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.SelectorNode;
import AST.TreeWalks.Exceptions.ScopeBoundsViolationException;
import AST.Visitor;
import SymbolTable.*;

public class ScopeCheckerVisitor implements Visitor {

    private SymbolTableInterface symbolTableInterface;
    private BlockScope currentBlockScope;
    private Scope currentSubScope;

    public ScopeCheckerVisitor(SymbolTableInterface symbolTableInterface) {
        this.symbolTableInterface = symbolTableInterface;
        this.currentBlockScope = null;
        this.currentSubScope = null;
    }

    @Override
    public void pre(int i, AbstractNode abstractNode) {
        NamedNode node = (NamedNode) abstractNode;
        NamedNode childNode = (NamedNode) node.getChild();

        // If needed, typecast
        String id = (node instanceof NamedIdNode) ? ((NamedIdNode) node).getId() : "";

        String childId = (childNode instanceof NamedIdNode) ? ((NamedIdNode) childNode).getId() : "";

        switch (node.getNodeEnum()) {
            case ROOT:
            case GROUP:
            case CHAIN:
            case PARAMS:
            case SIZE:
            case ASSIGN:
            case CHANNEL_IN:
            case CHANNEL_OUT:
            case SIZE_TYPE:
            case BLOCK_TYPE:
            case SOURCE_TYPE:
            case BLUEPRINT_TYPE:
            case OPERATION_TYPE:
            case DRAW:
            case BUILD:
                break;

            case BLOCK:
                this.currentBlockScope = this.symbolTableInterface.getBlockScope(id);
                break;

            case PROCEDURE:
                this.currentSubScope = this.currentBlockScope.getProcedureScope(id);
                break;

            case PROCEDURE_CALL:
                Scope scope = this.currentBlockScope.getProcedureScope(childId);
                if (scope == null) {
                    throw new ScopeBoundsViolationException("No such procedure: " + childId);
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
                    try {
                        this.verifyChannelVariable(childId);
                    } catch (ScopeBoundsViolationException e) {
                        this.verifyCurrentScopeVariable(childId);
                    }
                    
                } else if (ignoreSelector) {
                    // Do nothing.

                } else if (!(node.getParent() instanceof SelectorNode)) {
                    try {
                        this.verifyCurrentScopeVariable(id);
                    } catch (ScopeBoundsViolationException e) {
                        this.verifyChannelVariable(id);
                    }
                }
                break;
            default:
                throw new RuntimeException("Unexpected Node");
        }
    }

    @Override
    public void post(int i, AbstractNode abstractNode) {
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
            case CHANNEL_IN:
            case CHANNEL_OUT:
            case SIZE_TYPE:
            case BLOCK_TYPE:
            case SOURCE_TYPE:
            case BLUEPRINT_TYPE:
            case OPERATION_TYPE:
            case CHANNEL_DECLARATIONS:
            case ASSIGN:
                break;

            default:
                throw new RuntimeException("Unexpected Node");
        }
    }

    private void verifyChannelVariable(String id) {
        VariableEntry variable = this.currentBlockScope.getChannelDeclarationScope().getVariable(id);
        checkIfNull(variable, "Channeldeclarations", id);
    }

    private void verifyCurrentScopeVariable(String id) {
        VariableEntry variable = this.currentSubScope.getVariable(id);
        checkIfNull(variable, this.currentSubScope.getNode().getName(), id);
    }

    private void checkIfNull(VariableEntry variable, String subScope, String id) {
        if (variable == null) {
            throw new ScopeBoundsViolationException("In scope: " + subScope + ", no such variable: " + id);
        }
    }
}
