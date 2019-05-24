package AST.TreeWalks;

import AST.Nodes.AbstractNodes.Nodes.AbstractNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.BlockNode;
import AST.Nodes.NodeClasses.NamedNodes.ParamsNode;
import AST.TreeWalks.Exceptions.UnexpectedNodeException;
import SemanticAnalysis.Datastructures.HashSetStack;
import CompilerExceptions.SemanticExceptions.NoMainBlockException;
import CompilerExceptions.SemanticExceptions.SemanticProblemException;
import SymbolTableImplementation.SymbolTable;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class PreRecursiveVisitor extends ScopeTracker {
    private Set<BlockNode> buildNodes = new HashSet<>();


    public PreRecursiveVisitor(SymbolTable symbolTable) {
        super(symbolTable);
    }

    /**
     * @param printLevel   the level, used to decide how many indents there should be in the print statement.
     * @param abstractNode The node which is being visited.
     */
    @Override
    @SuppressWarnings("Duplicates")
    public void pre(int printLevel, AbstractNode abstractNode) {
        super.pre(printLevel, abstractNode);

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
                break;

            // Build node
            case BUILD:
                break;

            default:
                throw new UnexpectedNodeException(node);
        }
    }

    /**
     * @param printLevel   the level, used to decide how many indents there should be in the print statement.
     * @param abstractNode The node which is being visited.
     */
    @Override
    public void post(int printLevel, AbstractNode abstractNode) {
        super.post(printLevel, abstractNode);

        NamedNode node = (NamedNode) abstractNode;

        switch (node.getNodeEnum()) {
            // No action enums
            case ROOT:
                this.findMainBlocks(node);
                break;

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

    /**
     * Method used to start the block recursion testing check.
     * It will first find all potential main blocks, and then perform recursion checking on their build patterns.
     */
    private void findMainBlocks(AbstractNode errorNode) {
        // Find all potential main blocks
        List<BlockNode> potentialMainBlocks = this.findPotentialMainBlocks();

        // Loop all the potential main blocks and remove the once being build by other
        potentialMainBlocks.removeIf(this.buildNodes::contains);

        // Assert that there still is remaining potential main blocks
        this.assertNonZeroMainBlockCount(potentialMainBlocks, errorNode);

        // Store the remaining main blocks.
        this.symbolTable.setMainBlocks(potentialMainBlocks);

        // Assert no build recursions
        this.assertNoBuildCycles();
    }

    /**
     * Method used to start the no build cycle check, for all potential main blocks
     * @throws SemanticProblemException is there is build recursion within the block.
     */
    private void assertNoBuildCycles() {
        new RecursiveVisitor(this.symbolTable, new RecursiveBuildVisitor(new HashSetStack<>(), this.symbolTable)).startRecursiveWalk();
    }

    /**
     * Helper function for block recursion checking - Used for simplification.
     * Will throw an exception if no potential main blocks is present.
     * @param potentialMainBlocks A list of potential main blocks
     * @throws NoMainBlockException if no potential blocks is present in the list.
     */
    private void assertNonZeroMainBlockCount(List<BlockNode> potentialMainBlocks, AbstractNode errorNode) {
        if (potentialMainBlocks.size() == 0) {
            throw new NoMainBlockException(errorNode, "The supplied program have NO buildable blocks - All blocks require parameters or there is none.");
        }
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
}
