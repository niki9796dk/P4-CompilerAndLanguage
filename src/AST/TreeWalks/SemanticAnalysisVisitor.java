package AST.TreeWalks;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;
import AST.Nodes.NodeClasses.NamedNodes.*;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.*;
import AST.TreeWalks.Exceptions.RecursiveBlockException;
import AST.TreeWalks.Exceptions.UnexpectedNodeException;
import SemanticAnalysis.Exceptions.ChainConnectionMismatchException;
import SemanticAnalysis.Exceptions.GroupConnectionMismatchException;
import SemanticAnalysis.Exceptions.NoMainBlockException;
import SemanticAnalysis.Exceptions.SemanticProblemException;
import SemanticAnalysis.FlowChecker;
import SemanticAnalysis.Datastructures.HashSetStack;
import SemanticAnalysis.Datastructures.SetStack;
import SymbolTableImplementation.BlockScope;
import SymbolTableImplementation.Scope;
import SymbolTableImplementation.SymbolTableInterface;
import SymbolTableImplementation.VariableEntry;
import TypeChecker.Exceptions.ShouldNotHappenException;
import TypeChecker.TypeSystem;

import java.util.*;

/**
 * The visitor used for the semantic analysis phase of the compiler (Excluding type- and scope checking).
 */
public class SemanticAnalysisVisitor extends ScopeTracker {

    // Fields:
    private FlowChecker flowChecker;
    private Set<BlockNode> buildNodes;

    // Constants:
    private static final int UNARY_INPUT = 1;
    private static final int BINARY_INPUT = 2;

    /**
     * The constructor
     * @param symbolTable A symbol table, used for the scope tracker.
     */
    public SemanticAnalysisVisitor(SymbolTableInterface symbolTable) {
        super(symbolTable);
        this.flowChecker = new FlowChecker(symbolTable);
        this.buildNodes = new HashSet<>();
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
                break;

            case BUILD:
                this.addBuildBlockToSet((BuildNode) node);
                break;

            case CHAIN:
                this.verifyChain((ChainNode) node);
                this.extractMyChannelsUses(node);
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
                this.performBlockRecursionTesting();
                break;

            case BLOCK:
                this.flowChecker.check(this.currentBlockScope, this.currentSubScope);
                this.flowChecker.getConnected().clear();
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
     * Extracts all channel uses within a chain.
     * @param chainNode The ChainNode.
     */
    private void extractMyChannelsUses(AbstractNode chainNode) {
        AbstractNode child = chainNode.getChild();

        while (child != null) {
            this.handleChannel(child);
            child = child.getSib();
        }
    }

    /**
     * Helper function used to extract all channel uses within a chain.
     * If node is instance of a group, all the group children are handled recursively,
     * else if it's a selector we first check that the selector is for a channel, and then handle that selector,
     * and ignore everything else.
     * @param node A single chain element
     */
    private void handleChannel(AbstractNode node) {
        if (node instanceof GroupNode) {
            for (AbstractNode child = node.getChild(); child != null; child = child.getSib()) {
                this.handleChannel(child);
            }
        } else if (node instanceof SelectorNode) {

            NodeEnum nodeSuperType = this.typeSystem.getSuperTypeOfNode(node, this.currentBlockScope, this.currentSubScope);

            if (nodeSuperType == NodeEnum.CHANNEL_IN_MY || nodeSuperType == NodeEnum.CHANNEL_OUT_MY) {
                this.handleSelector((SelectorNode) node);
            }

        }
    }

    /**
     * Helper function used to extract all channel uses within a chain.
     * Depending on the type of the selector different behaviour is executed.
     * If it's a straight channel access, the ID is simply stored,
     * else if it's a selector for another selector, we recursively follow the selectors until we find the channel in use.
     * @param node The SelectorNode to extract channel uses from.
     */
    private void handleSelector(SelectorNode node) {
        boolean isThis = ("this").equals(node.getId());

        String prefix = flowChecker.channelPrefix(node, currentBlockScope, currentSubScope);

        if (isThis) {
            String childId = ((NamedIdNode) node.getChild()).getId();

            flowChecker.getConnected().add(prefix + childId);
        } else {
            Scope scope = this.symbolTable.getSubScope(this.currentBlockScope, this.currentSubScope);
            VariableEntry localVariable = scope.getVariable(node);

            boolean isLocalVariable = localVariable != null;

            if (isLocalVariable) {
                SelectorNode subtypeNode = (SelectorNode) localVariable.getSubType(node.getNumber());
                this.handleSelector(subtypeNode);

            } else {
                flowChecker.getConnected().add(prefix + node.getId());
            }
        }
    }

    /**
     * Method used to start the block recursion testing check.
     * It will first find all potential main blocks, and then perform recursion checking on their build patterns.
     */
    private void performBlockRecursionTesting() {
        // Find all potential main blocks
        List<BlockNode> potentialMainBlocks = this.findPotentialMainBlocks();

        // Loop all the potential main blocks and remove the once being build by other
        potentialMainBlocks.removeIf(this.buildNodes::contains);

        // Assert that there still is remaining potential main blocks
        this.assertNonZeroMainBlockCount(potentialMainBlocks);

        // For every actual potential main block, verify that there is no build cycles.
        for (BlockNode mainBlock : potentialMainBlocks) {
            this.assertNoBuildCycles(mainBlock);
        }
    }

    /**
     * Helper function for block recursion checking - Used for simplification.
     * Will throw an exception if no potential main blocks is present.
     * @param potentialMainBlocks A list of potential main blocks
     * @throws NoMainBlockException if no potential blocks is present in the list.
     */
    private void assertNonZeroMainBlockCount(List<BlockNode> potentialMainBlocks) {
        if (potentialMainBlocks.size() == 0) {
            throw new NoMainBlockException("The supplied program have NO buildable blocks - All blocks require parameters or there is none.");
        }
    }

    /**
     * Method used to start the no build cycle check, for a single potential main block.
     * @param mainBlock A potential main block.
     * @throws SemanticProblemException is there is build recursion within the block.
     */
    private void assertNoBuildCycles(BlockNode mainBlock) {
        mainBlock.walkTree(new RecursiveBuildVisitor(new HashSetStack<>(), this.symbolTable));
    }

    /**
     * Helper function for performing build recursion testing.
     * Simply adds a block to a set over blocks that have been build by other blocks.
     * @param node The BuildNode.
     */
    private void addBuildBlockToSet(BuildNode node) {
        String buildSubType = this.typeSystem.getSubTypeOfNode(node, this.currentBlockScope, this.currentSubScope);

        if (this.symbolTable.isPredefinedSource(buildSubType) || this.symbolTable.isPredefinedOperation(buildSubType)) {
            return; // Simply ignore this case.
        }

        BlockNode blockBeingBuild = this.typeSystem.getBlock(buildSubType);

        this.buildNodes.add(blockBeingBuild);
    }

    /**
     * Loops all block definition, and find all those whoes blueprint does not take any parameters.
     * Blocks with no build parameters are called potential main blocks, since these are the blocks that are fully static
     * and does not depend on anything else than them self to supply data, to any sub block.
     * @return A list of potential main blocks.
     */
    private List<BlockNode> findPotentialMainBlocks() {
        // Instantiate the list
        List<BlockNode> potentialMainBlocks = new LinkedList<>(); /* Linked list is used, since we only ever iterate it. */

        // Find the very fist block definition
        BlockNode currentBlock = (BlockNode) this.symbolTable.getLatestBlockScope().getNode().getFirstSib();

        // Loop all block definition and if they are a potential main block, store it in the list
        for (/* Do Nothing */ ; currentBlock != null; currentBlock = (BlockNode) currentBlock.getSib()) {
            boolean isPotentialMainBlock = this.checkIfBlockCouldBeAMainBlock(currentBlock);

            if (isPotentialMainBlock) {
                potentialMainBlocks.add(currentBlock);
            }
        }

        // Return the list
        return potentialMainBlocks;
    }

    /**
     * Checks if a single given block is a potential main block, by verifying that it does not have any build parameters
     * for it's blueprint
     * @param block The block to check
     * @return Returns true if the block does not have any build parameters, and false if it has more than zero (0).
     */
    private boolean checkIfBlockCouldBeAMainBlock(BlockNode block) {
        // Find the blueprint node within the block
        AbstractNode blueprintNode = this.symbolTable
                .getBlockScope(block.getId())
                .getBlueprintScope()
                .getNode();

        // Find the parameter node, if such one exsist.
        ParamsNode paramNode = blueprintNode.findFirstChildOfClass(ParamsNode.class);

        // Count the amount of param nodes
        int blueprintParamCount = (paramNode != null) ? paramNode.countChildren() : 0;

        // Return true if there are no parameters, and therefor could be a potential main block
        return blueprintParamCount == 0;
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

    public FlowChecker getFlowChecker() {
        return flowChecker;
    }

    public Set<BlockNode> getBuildNodes() {
        return buildNodes;
    }
}
