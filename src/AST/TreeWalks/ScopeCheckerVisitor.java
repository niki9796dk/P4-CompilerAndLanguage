package AST.TreeWalks;

import AST.Nodes.AbstractNodes.AbstractNode;
import AST.Nodes.AbstractNodes.NamedIdNode;
import AST.Nodes.AbstractNodes.NamedNode;
import AST.Nodes.SelectorNode;
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

        switch (node.getNodeEnum()) {
            case ROOT:
            case GROUP:
            case CHAIN:
            case PROCEDURE_CALL:
            case PARAMS:
            case DRAW:
            case BUILD:
            case SIZE:
            case ASSIGN:
                break;

            case BLUEPRINT:
            case PROCEDURE:
            case CHANNEL_DECLARATIONS:
                this.currentSubScope = this.currentBlockScope.getScope().getEntry(((NamedIdNode) node).getId());
                break;

            case BLOCK:
                this.currentBlockScope = this.symbolTableInterface.getBlockScope(((NamedIdNode) node).getId());
                break;

            case CHANNEL_IN:
            case CHANNEL_OUT:
            case SIZE_TYPE:
            case BLOCK_TYPE:
            case SOURCE_TYPE:
            case BLUEPRINT_TYPE:
            case OPERATION_TYPE:
                break;

            case SELECTOR:
                String id = ((NamedIdNode) node).getId();
                if ("this".equals(id)) {
                    String childId = ((NamedIdNode) node.getChild()).getId();
                    this.verifyChannelVariable(childId);
                } else if (!(node.getParent() instanceof SelectorNode)) {

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
