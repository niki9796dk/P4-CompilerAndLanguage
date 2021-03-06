package AST.TreeWalks;

import AST.Nodes.AbstractNodes.Nodes.AbstractNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNode;
import AST.Nodes.NodeClasses.NamedNodes.ChainNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.BuildNode;
import AST.Nodes.NodeClasses.NamedNodes.ProcedureCallNode;
import CompilerExceptions.UnexpectedNodeException;
import SemanticAnalysis.Datastructures.ProcCall;
import SemanticAnalysis.FlowChecker;
import SymbolTableImplementation.SymbolTable;

/**
 * The visitor used for the semantic analysis phase of the compiler (Excluding type- and scope checking).
 */
public class FlowCheckVisitor extends ScopeTracker {

    // Fields:
    private FlowChecker currentFlow;

    /**
     * The constructor
     *
     * @param symbolTable A symbol table, used for the scope tracker.
     */
    public FlowCheckVisitor(SymbolTable symbolTable) {
        super(symbolTable);
        FlowChecker.setup(symbolTable);
    }

    /**
     * The preorder walk of the visistor
     *
     * @param printLevel the level, used to decide how many indents there should be in the print statement.
     * @param node       The node which is being visited.
     */
    @Override
    public void pre(int printLevel, AbstractNode node) {
        // Update the scope tracker
        super.pre(printLevel, node);

        // Perform visitor
        switch (((NamedNode) node).getNodeEnum()) {
            case ROOT:
                FlowChecker.setup(symbolTable);
                break;
            case BLOCK:
                break;

            case BLUEPRINT:
                currentFlow = FlowChecker.startBlock(currentBlockScope);
                break;

            case PROCEDURE:
            case CHANNEL_DECLARATIONS:
            case GROUP:
            case ASSIGN:
            case CHANNEL_IN_TYPE:
            case CHANNEL_OUT_TYPE:
            case PARAMS:
            case DRAW:
            case SIZE:
            case SIZE_TYPE:
            case BLOCK_TYPE:
            case SOURCE_TYPE:
            case BLUEPRINT_TYPE:
            case OPERATION_TYPE:
            case SELECTOR:
            case CHANNEL_IN_MY:
            case CHANNEL_OUT_MY:
                break;

            case PROCEDURE_CALL:
                this.currentFlow.pushProcCall(new ProcCall((ProcedureCallNode) node, typeSystem, currentBlockScope, currentSubScope));
                break;

            case BUILD:
                this.currentFlow.addBuild((BuildNode) node, currentSubScope);
                break;

            case CHAIN:
                this.currentFlow.addChain((ChainNode) node, currentSubScope);
                break;

            default:
                throw new UnexpectedNodeException((NamedNode) node);
        }
    }

    /**
     * The post order walk of the visitor
     *
     * @param printLevel   the level, used to decide how many indents there should be in the print statement.
     * @param abstractNode The node which is being visited.
     */
    @Override
    public void post(int printLevel, AbstractNode abstractNode) {
        // Update the scope tracker
        super.post(printLevel, abstractNode);

        // Perform visitor
        NamedNode node = (NamedNode) abstractNode;

        switch (node.getNodeEnum()) {

            // No action enums
            case ROOT:
                break;

            case BLUEPRINT:
                currentFlow = currentFlow.evaluateBlock(node);
                break;

            case PROCEDURE_CALL:
                this.currentFlow.popProcCall();
                break;

            case PROCEDURE:
            case BLOCK:
            case GROUP:
            case BUILD:
            case CHAIN:
            case PARAMS:
            case SELECTOR:
            case DRAW:
            case SIZE:
            case ASSIGN:
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

            default:
                throw new UnexpectedNodeException(node);
        }
    }
}