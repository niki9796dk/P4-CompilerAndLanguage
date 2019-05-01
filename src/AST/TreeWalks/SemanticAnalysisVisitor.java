package AST.TreeWalks;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;
import AST.Nodes.NodeClasses.NamedNodes.ChannelDeclarationsNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.MyInChannelNode;
import AST.Nodes.NodeClasses.NamedNodes.ParamsNode;
import AST.TreeWalks.Exceptions.RecursiveBlockException;
import AST.TreeWalks.Exceptions.UnexpectedNodeException;
import AST.Visitor;
import SemanticAnalysis.FlowChecker;
import SemanticAnalysis.Datastructures.HashSetStack;
import SemanticAnalysis.Datastructures.SetStack;
import SemanticAnalysis.SemanticProblemException;
import SymbolTableImplementation.BlockScope;
import SymbolTableImplementation.SymbolTableInterface;
import TypeChecker.Exceptions.ShouldNotHappenException;
import TypeChecker.TypeSystem;

public class SemanticAnalysisVisitor implements Visitor {

    private FlowChecker flowChecker;
    private SymbolTableInterface symbolTableInterface;
    private TypeSystem typeSystem;
    private String currentBlockScope;
    private String currentSubScope;

    public SemanticAnalysisVisitor(SymbolTableInterface symbolTableInterface) {
        this.flowChecker = new FlowChecker();
        this.symbolTableInterface = symbolTableInterface;
        this.typeSystem = new TypeSystem(this.symbolTableInterface);
    }

    @Override
    public void pre(int printLevel, AbstractNode abstractNode) {

        NamedNode node = (abstractNode instanceof NamedNode) ? (NamedNode) abstractNode : null;
        NamedNode namedIdNode = (abstractNode instanceof NamedIdNode) ? (NamedIdNode) abstractNode : null;

        // If needed, typecast
        String id = (node instanceof NamedIdNode) ? ((NamedIdNode) node).getId() : "no id";

        SetStack<String> callStack = new HashSetStack<>();

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

    /**
     * Verifies that a group connection size matches whatever it connects to.
     * @param groupNode The group node, from which we compare
     * @param rightNode The right side node, from which the group node connects to.
     */
    private void verifyGroupConnection(AbstractNode groupNode, AbstractNode rightNode) {
        // Verify that the right side node, is of the allowed types (Block and operation)
        this.assertCorrectRightNodeType(rightNode);

        // Count the amount of connections given and expected
        int groupChildrenCount = groupNode.countChildren();
        int rightNodeChildrenCount = this.countInChannels(rightNode);

        // Compare the two values, and throw an exception if something is wrong.
        if (groupChildrenCount != rightNodeChildrenCount) {
            throw new SemanticProblemException("Group connection size mismatch: " + groupNode + ":" + groupChildrenCount + " vs. " + rightNode + ":" + rightNodeChildrenCount);
        }
    }

    /**
     * Asserts that the right side node of a group connection is of the correct type.
     * This should never be a problem, since it should have been caught in the type checker
     * @param rightNode The node to verify
     * @throws ShouldNotHappenException Is Thrown if the wrong type is connected to by the group.
     */
    private void assertCorrectRightNodeType(AbstractNode rightNode) {
        switch (this.typeSystem.getSuperTypeOfNode(rightNode, this.currentBlockScope, this.currentSubScope)) {
            case OPERATION_TYPE:
            case BLOCK_TYPE:
                // Everything cool
                break;

            default:
                throw new ShouldNotHappenException("Whatever the group connects to, is NOT ALLOWED: " + rightNode);
        }
    }

    /**
     * Counts the amount of in channels in a given right side node, of either type operation or block.
     * @param rightNode The node to count channels from
     * @return The amount of in channels in the given node.
     */
    private int countInChannels(AbstractNode rightNode) {
        if (this.typeSystem.getSuperTypeOfNode(rightNode, this.currentBlockScope, this.currentSubScope) == NodeEnum.OPERATION_TYPE) {
            return this.countInChannelsOfOperation(rightNode);
        } else {
            return this.countInChannelsOfBlock(rightNode);
        }
    }

    /**
     * Counts the amount of mychannel:in declaration is present in a block, and returns that integer value.
     * @param rightNode The block, from which should be counted from.
     * @return The amount of mychannel:in declarations in the given block
     */
    private int countInChannelsOfBlock(AbstractNode rightNode) {
        String blockId = this.typeSystem.getSubTypeOfNode(rightNode, this.currentBlockScope, this.currentSubScope);

        // Get the channel declaration node of the block
        ChannelDeclarationsNode channelDeclaNode = (ChannelDeclarationsNode) this.symbolTableInterface.getBlockScope(blockId).getChannelDeclarationScope().getNode();

        // Loop all children (Channel declarations), and if it's an input channel, increment the counter
        AbstractNode child = channelDeclaNode.getChild();

        int inChannelCount = 0;

        while (child != null) {
            if (child instanceof MyInChannelNode) {
                inChannelCount++;
            }
            child = child.getSib();
        }

        // Return the amount of in channels.
        return inChannelCount;
    }

    /**
     * Counts and return the amount of channels within an operation.
     * @param rightNode The operation node
     * @return The amount of in channels within the operation.
     */
    private int countInChannelsOfOperation(AbstractNode rightNode) {
        return 2; // TODO: Connect this to some definition of operations.
    }
}
