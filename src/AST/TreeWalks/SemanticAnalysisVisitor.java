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

    public SemanticAnalysisVisitor() {
        this.flowChecker = new FlowChecker();
    }

    @Override
    public void pre(int printLevel, AbstractNode abstractNode) {

        NamedNode node = (abstractNode instanceof NamedNode) ? (NamedNode) abstractNode : null;
        NamedNode namedIdNode = (abstractNode instanceof NamedIdNode) ? (NamedIdNode) abstractNode : null;

        // If needed, typecast
        String id = (node instanceof NamedIdNode) ? ((NamedIdNode) node).getId() : "no id";

        SetStack<String> callStack = new HashSetStack<>();

        switch (node.getNodeEnum()) {
            // No action enums
            case BLOCK:
            case BLUEPRINT:
            case PROCEDURE:
            case CHANNEL_DECLARATIONS:
            case GROUP:
            case ASSIGN:
            case BUILD:
                buildRecursionCheck(node, callStack);
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


    private void buildRecursionCheck(NamedNode node, SetStack<String> callStack){
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

        if (!callStack.push(builder.toString())){
            throw new RecursiveBlockException(childNode.toString());
        }
    }
}
