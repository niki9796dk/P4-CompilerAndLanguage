package AST.TreeWalks;

import AST.Nodes.AbstractNodes.Nodes.AbstractNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;
import AST.Nodes.NodeClasses.NamedNodes.ParamsNode;
import AST.TreeWalks.Exceptions.UnexpectedNodeException;
import AST.Visitor;
import SemanticAnalysis.Datastructures.SetStack;
import SemanticAnalysis.Exceptions.SemanticProblemException;
import SymbolTableImplementation.BlockScope;
import SymbolTableImplementation.Scope;
import SymbolTableImplementation.SymbolTableInterface;
import TypeChecker.TypeSystem;

public abstract class ScopeTracker implements Visitor {
    protected String currentBlockScope;
    protected String currentSubScope;
    protected SymbolTableInterface symbolTable;
    protected TypeSystem typeSystem;

    public ScopeTracker(SymbolTableInterface symbolTable) {
        this.symbolTable = symbolTable;
        this.typeSystem = new TypeSystem(symbolTable);
    }

    /**
     * @param printLevel   the level, used to decide how many indents there should be in the print statement.
     * @param node The node which is being visited.
     */
    @Override
    public void pre(int printLevel, AbstractNode node) {
        NamedNode namedNode = (NamedNode) node;

        switch (namedNode.getNodeEnum()) {
            // Location enums
            case BLOCK:
                this.currentBlockScope = ((NamedIdNode) node).getId();
                break;
            case BLUEPRINT:
                this.currentSubScope = BlockScope.BLUEPRINT;
                break;
            case PROCEDURE:
                this.currentSubScope = BlockScope.PROCEDURE_PREFIX + ((NamedIdNode) node).getId();
                break;
            case CHANNEL_DECLARATIONS:
                this.currentSubScope = BlockScope.CHANNELS;
                break;

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
            case CHANNEL_IN_MY:
            case CHANNEL_OUT_MY:
            case CHANNEL_IN_TYPE:
            case CHANNEL_OUT_TYPE:
            case SIZE_TYPE:
            case BLOCK_TYPE:
            case SOURCE_TYPE:
            case BLUEPRINT_TYPE:
            case OPERATION_TYPE:
            case BUILD:
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
    public void post(int printLevel, AbstractNode abstractNode) {
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
            case PROCEDURE:
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
            case BUILD:
                break;

            default:
                throw new UnexpectedNodeException(node);
        }
    }

}
