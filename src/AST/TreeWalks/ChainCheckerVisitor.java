package AST.TreeWalks;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNode;
import AST.Nodes.NodeClasses.NamedNodes.*;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.*;
import AST.TreeWalks.Exceptions.UnexpectedNodeException;
import SemanticAnalysis.Exceptions.ChainConnectionMismatchException;
import SemanticAnalysis.Exceptions.GroupConnectionMismatchException;
import SymbolTableImplementation.*;
import TypeChecker.Exceptions.ShouldNotHappenException;

import java.util.*;

/**
 * The visitor used for the semantic analysis phase of the compiler (Excluding type- and scope checking).
 */
public class ChainCheckerVisitor extends ScopeTracker {

    // Fields:

    // Constants:
    private static final int UNARY_INPUT = 1;
    private static final int BINARY_INPUT = 2;

    /**
     * The constructor
     * @param symbolTable A symbol table, used for the scope tracker.
     */
    public ChainCheckerVisitor(SymbolTable symbolTable) {
        super(symbolTable);
    }

    /**
     * The preorder walk of the visistor
     * @param printLevel    the level, used to decide how many indents there should be in the print statement.
     * @param node          The node which is being visited.
     */
    @Override
    public void pre(int printLevel, AbstractNode node) {
        // Update the scope tracker
        super.pre(printLevel, node);

        // Perform visitor
        switch (((NamedNode) node).getNodeEnum()) {
            // No action enums
            case BLOCK:
            case BLUEPRINT:
            case PROCEDURE:
            case CHANNEL_DECLARATIONS:
            case GROUP:
            case ASSIGN:
            case CHANNEL_IN_TYPE:
            case CHANNEL_OUT_TYPE:
            case PROCEDURE_CALL:
            case PARAMS:
            case DRAW:
            case SIZE:
            case ROOT:
            case SIZE_TYPE:
            case BLOCK_TYPE:
            case SOURCE_TYPE:
            case BLUEPRINT_TYPE:
            case OPERATION_TYPE:
            case SELECTOR:
            case CHANNEL_IN_MY:
            case CHANNEL_OUT_MY:
            case BUILD:
                break;

            case CHAIN:
                this.verifyChain((ChainNode) node);
                break;

            default:
                throw new UnexpectedNodeException((NamedNode) node);
        }
    }

    /**
     * The post order walk of the visitor
     * @param printLevel    the level, used to decide how many indents there should be in the print statement.
     * @param abstractNode  The node which is being visited.
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

            case BLOCK:
                break;

            case GROUP:
            case BUILD:
            case CHAIN:
            case PROCEDURE_CALL:
            case PARAMS:
            case SELECTOR:
            case DRAW:
            case SIZE:
            case ASSIGN:
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


    /**
     * Verify every child connection within a chain.
     * @param chainNode The chain node to verify.
     */
    private void verifyChain(ChainNode chainNode) {
        AbstractNode child = chainNode.getChild();

        while (child.getSib() != null) {
            this.verifyConnection(child, child.getSib());
            child = child.getSib();
        }
    }

    /**
     * Verifies that a connection between two nodes, is acceptable in terms of channel sizes.
     * @param leftNode The left side node of the chain: ->
     * @param rightNode The right side node of the chain: ->
     */
    private void verifyConnection(AbstractNode leftNode, AbstractNode rightNode) {
        // If left node is a group, then verify it as a group connection
        if (leftNode instanceof GroupNode) {
            this.verifyGroupConnection(leftNode, rightNode);

        } else {
            // Else verify it as an ordinary connection
            int leftOut = this.countOutChannels(leftNode);
            int rightIn = this.countInChannels(rightNode);

            if (leftOut != 1 || rightIn != 1) {
                throw new ChainConnectionMismatchException("A chain was used on an element with more than 1 in/out channel: " + leftNode + "[" + leftOut + "] -> " + rightNode + "[" + rightIn + "]");
            }
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
            throw new GroupConnectionMismatchException("Group connection size mismatch: " + groupNode + ":" + groupChildrenCount + " vs. " + rightNode + ":" + rightNodeChildrenCount);
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
        NodeEnum superType = this.typeSystem.getSuperTypeOfNode(rightNode, this.currentBlockScope, this.currentSubScope);

        switch (superType) {
            case OPERATION_TYPE:
                return this.countInChannelsOfOperation(rightNode);

            case BLOCK_TYPE:
                return this.countInChannelsOfBlock(rightNode);

            case SOURCE_TYPE:
                return 0;

            case CHANNEL_IN_TYPE:
            case CHANNEL_OUT_TYPE:
            case CHANNEL_IN_MY:
            case CHANNEL_OUT_MY:
                return 1;

            default:
                throw new UnexpectedNodeException(superType);
        }

    }

    /**
     * Counts the amount of in channels in a given right side node, of either type operation or block.
     * @param rightNode The node to count channels from
     * @return The amount of in channels in the given node.
     */
    private int countOutChannels(AbstractNode rightNode) {
        NodeEnum superType = this.typeSystem.getSuperTypeOfNode(rightNode, this.currentBlockScope, this.currentSubScope);

        switch (superType) {
            case OPERATION_TYPE:
                return this.countOutChannelsOfOperation(rightNode);

            case BLOCK_TYPE:
                return this.countOutChannelsOfBlock(rightNode);

            case SOURCE_TYPE:
            case CHANNEL_IN_TYPE:
            case CHANNEL_OUT_TYPE:
            case CHANNEL_IN_MY:
            case CHANNEL_OUT_MY:
                return 1;

            default:
                throw new UnexpectedNodeException(superType);
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
        ChannelDeclarationsNode channelDeclaNode = (ChannelDeclarationsNode) this.symbolTable.getBlockScope(blockId).getChannelDeclarationScope().getNode();

        // Count the amount of children of type MyInChannel
        return channelDeclaNode.countChildrenInstanceOf(MyInChannelNode.class);
    }

    /**
     * Counts the amount of mychannel:out declaration is present in a block, and returns that integer value.
     * @param rightNode The block, from which should be counted from.
     * @return The amount of mychannel:out declarations in the given block
     */
    private int countOutChannelsOfBlock(AbstractNode rightNode) {
        String blockId = this.typeSystem.getSubTypeOfNode(rightNode, this.currentBlockScope, this.currentSubScope);

        // Get the channel declaration node of the block
        ChannelDeclarationsNode channelDeclaNode = (ChannelDeclarationsNode) this.symbolTable.getBlockScope(blockId).getChannelDeclarationScope().getNode();

        // Count the amount of children of type MyInChannel
        return channelDeclaNode.countChildrenInstanceOf(MyOutChannelNode.class);
    }

    /**
     * Counts and return the amount of in channels within an operation.
     * @param rightNode The operation node
     * @return The amount of in channels within the operation.
     */
    private int countInChannelsOfOperation(AbstractNode rightNode) {
        switch (this.typeSystem.getSubTypeOfNode(rightNode, this.currentBlockScope, this.currentSubScope)) {
            case "Addition":
            case "Multiplication":
            case "Subtraction":
            case "_Addition":
            case "_Multiplication":
            case "_Subtraction":
            case "_Division":
                return BINARY_INPUT;

            case "_Sigmoid":
            case "_Tanh":
            case "_Relu":
            case "Transpose":
                return UNARY_INPUT;

            default:
                throw new ShouldNotHappenException("We were checking a non exsistent operation for it's channels: " + rightNode);
        }
    }

    /**
     * Counts and return the amount of out channels within an operation.
     * @param rightNode The operation node
     * @return The amount of out channels within the operation.
     */
    private int countOutChannelsOfOperation(AbstractNode rightNode) {
        return 1; // TODO: Connect this to some definition of operations.
    }
    public Set<BlockNode> getBuildNodes() {
        return null;
    }
}
