package AST.TreeWalks;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;
import AST.Nodes.NodeClasses.NamedNodes.GroupNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.SelectorNode;
import AST.Nodes.NodeClasses.NamedNodes.ParamsNode;
import AST.TreeWalks.Exceptions.RecursiveBlockException;
import AST.TreeWalks.Exceptions.UnexpectedNodeException;
import AST.Visitor;
import SemanticAnalysis.FlowChecker;
import SemanticAnalysis.Datastructures.HashSetStack;
import SemanticAnalysis.Datastructures.SetStack;
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
    private SetStack<String> callStack;

    public SemanticAnalysisVisitor(SymbolTableInterface symbolTableInterface) {
        this.flowChecker = new FlowChecker();
        this.symbolTableInterface = symbolTableInterface;
        callStack = new HashSetStack<>();
    }

    @Override
    public void pre(int printLevel, AbstractNode abstractNode) {

        NamedNode node = (abstractNode instanceof NamedNode) ? (NamedNode) abstractNode : null;
        NamedIdNode namedIdNode = (abstractNode instanceof NamedIdNode) ? (NamedIdNode) abstractNode : null;
        if (node == null){
            throw new RuntimeException("How did this node get here?? " + abstractNode.toString());
        }

        // If needed, typecast
        String id = (node instanceof NamedIdNode) ? ((NamedIdNode) node).getId() : "no id";

        switch (node.getNodeEnum()) {
            // Location enums
            case BLOCK:
                this.currentBlockScope = namedIdNode.getId();
                break;
            case BLUEPRINT:
                this.currentSubScope = BlockScope.BLUEPRINT;
                break;
            case PROCEDURE:
                this.currentSubScope = BlockScope.PROCEDURE_PREFIX + namedIdNode.getId();
                break;
            case CHANNEL_DECLARATIONS:
                this.currentSubScope = BlockScope.CHANNELS;
                break;

            // No action enums
            case GROUP:
            case ASSIGN:
                break;

            case BUILD:
                buildRecursionCheck(node);
                break;

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
            case BUILD:
                callStack.pop();
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


    private void buildRecursionCheck(NamedNode node){
        StringBuilder builder = new StringBuilder();
        AbstractNode childNode = node.findFirstChildOfClass(ParamsNode.class).getChild();

        builder.append(node.getName());
        while (childNode.getSib() != null) {
            if(childNode instanceof NamedIdNode){
                builder.append(childNode.getName() + ((NamedIdNode) childNode).getId());
            } else if (childNode instanceof NamedNode){
                builder.append(childNode.getName());
            }
            childNode = childNode.getSib();
        }

        if (!this.callStack.push(builder.toString())){
            throw new RecursiveBlockException(childNode.toString());
        }
    }

    private void verifyGroupConnection(AbstractNode groupNode, AbstractNode rightNode) {

        int groupChildrenCount = groupNode.countChildren();



    }

    private void countChannelsOfBlock() {

    }
}
