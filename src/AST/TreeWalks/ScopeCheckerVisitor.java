package AST.TreeWalks;

import AST.Nodes.AbstractNodes.Nodes.AbstractNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNode;
import AST.Nodes.NodeClasses.NamedNodes.ProcedureCallNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.SelectorNode;
import AST.TreeWalks.Exceptions.NonexistentBlockException;
import AST.TreeWalks.Exceptions.RecursiveBlockException;
import AST.TreeWalks.Exceptions.ScopeBoundsViolationException;
import AST.TreeWalks.Exceptions.UnexpectedNodeException;
import AST.Visitor;
import SymbolTableImplementation.*;

import java.util.concurrent.RecursiveAction;

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
                // The given id is valid IF:
                // it is an existent block, AND not itself
                if ((this.symbolTableInterface.getBlockScope(id) != null && !(this.symbolTableInterface.getBlockScope(id).getId().equals(childId)))
                        // or it is an operation
                        || this.symbolTableInterface.isPredefinedOperation(id)
                        // or it is a source
                        || this.symbolTableInterface.isPredefinedSource(id))
                {
                    // Nothing
                    if(this.symbolTableInterface.getBlockScope(id) != null){
                        AbstractNode nodebuffer;
                        nodebuffer = node.getParent();
                        System.out.println(nodebuffer.toString());
                        while (nodebuffer instanceof NamedIdNode){
                            if(((NamedIdNode)nodebuffer).getId().equals(id)){
                                throw new RecursiveBlockException();
                            }
                            nodebuffer = nodebuffer.getParent();
                            System.out.println(nodebuffer.toString());
                        }
                    }

                } else {
                    throw new NonexistentBlockException(node.toString());
                }
                break;

            case BLOCK:
                this.currentBlockScope = this.symbolTableInterface.getBlockScope(id);
                break;

            case PROCEDURE:
                this.currentSubScope = this.currentBlockScope.getProcedureScope(id);
                break;

            case PROCEDURE_CALL:
                SelectorNode childSelector = (SelectorNode) node.findFirstChildOfClass(SelectorNode.class);

                Scope scope = this.currentBlockScope.getProcedureScope(childSelector.getId());
                if (scope == null) {
                    throw new ScopeBoundsViolationException("No such procedure: " + childSelector.getId());
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
                    } catch (ScopeBoundsViolationException e) {
                        this.verifyChannelVariable(id);
                    }
                }
                break;
            default:
                throw new UnexpectedNodeException(node);
        }
    }

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
            throw new ScopeBoundsViolationException("In scope: " + this.currentBlockScope.getId() + ">" + subScope + ", no such variable: " + id);
        }
    }
}
