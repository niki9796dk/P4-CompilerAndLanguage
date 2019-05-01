package AST.TreeWalks;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;
import AST.Nodes.NodeClasses.NamedNodes.GroupNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.SelectorNode;
import AST.Nodes.NodeClasses.NamedNodes.ParamsNode;
import AST.TreeWalks.Exceptions.UnexpectedNodeException;
import AST.Visitor;
import SemanticAnalysis.FlowChecker;
import SymbolTableImplementation.BlockScope;
import SymbolTableImplementation.SymbolTableInterface;
import TypeChecker.Exceptions.ParamsInconsistencyException;
import TypeChecker.Exceptions.ShouldNotHappenException;
import TypeChecker.Exceptions.TypeInconsistencyException;
import TypeChecker.TypeSystem;

public class SemanticAnalysisVisitor implements Visitor {

    private FlowChecker flowChecker;
    private SymbolTableInterface symbolTableInterface;
    private String currentBlockScope;
    private String currentSubScope;

    public SemanticAnalysisVisitor(SymbolTableInterface symbolTableInterface) {
        this.flowChecker = new FlowChecker();
        this.symbolTableInterface = symbolTableInterface;
    }

    @Override
    public void pre(int printLevel, AbstractNode abstractNode) {
        NamedNode node = (NamedNode) abstractNode;

        switch (node.getNodeEnum()) {
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
            case GROUP:
            case ASSIGN:
            case BUILD:
            case PROCEDURE_CALL:
            case PARAMS:
            case DRAW:
            case SIZE:
            case SELECTOR:
            case ROOT:
            case SIZE_TYPE:
            case BLOCK_TYPE:
            case SOURCE_TYPE:
            case BLUEPRINT_TYPE:
            case OPERATION_TYPE:
                break;

            case CHAIN:
                break;

            // Channels
            case CHANNEL_IN_MY:
            case CHANNEL_OUT_MY:
            case CHANNEL_IN_TYPE:
            case CHANNEL_OUT_TYPE:

                String id = ((NamedIdNode) node).getId();
                flowChecker.getChannels().add(id);

                break;

            default:
                throw new UnexpectedNodeException(node);
        }
    }

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
            case BUILD:
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
                break;

            default:
                throw new UnexpectedNodeException(node);
        }
    }

    private void verifyGroupConnection(AbstractNode groupNode, AbstractNode rightNode) {

        int groupChildrenCount = groupNode.countChildren();



    }

    private void countChannelsOfBlock() {

    }
}
