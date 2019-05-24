package SymbolTableImplementation;

import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.BlockNode;
import CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.BinaryOperations.BinaryAbstractOperation;
import CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.NullaryOperation.NullaryAbstractOperation;
import CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.UnaryOperations.UnaryAbstractOperation;
import Enums.AnsiColor;
import ScopeChecker.Exceptions.NoSuchVariableDeclaredException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * An implementation of a SymbolTable, which contains the scopes of blocks.
 */
public class SymbolTable implements SymbolTableInterface {
    /**
     * The state of the symbol table.
     */
    private NamedTable<BlockScope> blockTable = new NamedTable<>();

    private List<BlockNode> mainBlocks;

    /**
     * Constructs an empty {@code SymbolTable} with the default initial capacity
     */
    public SymbolTable() {
    }

    /**
     * Get the list of main blocks
     * @return List of main blocks
     */
    public List<BlockNode> getMainBlocks() {
        return mainBlocks;
    }

    /**
     * Store the list of main blocks
     * @param mainBlocks The list of main blocks.
     */
    public void setMainBlocks(List<BlockNode> mainBlocks) {
        this.mainBlocks = mainBlocks;
    }

    /**
     * Adds a block table to the symbol table
     *
     * @param node The node to add to the symbol table.
     */
    @Override
    public void openBlockScope(BlockNode node) {
        String blockId = node.getId();

        this.blockTable.setEntry(blockId, new BlockScope(blockId, node));
    }

    /**
     * Opens a sub scope within the latest block scope
     *
     * @param node The subnode to open within the most recent block scope.
     */
    @Override
    public void openSubScope(NamedNode node) {

        String scopeName = this.getScopeNameFromNode(node);

        this.blockTable.getLatest().openScope(scopeName, node);
    }

    /**
     * Gets a specific BlockScope from an id
     *
     * @param id The id of the desired block scope.
     * @return The block scope with the id, or Null if no such block scope exists.
     */
    @Override
    public BlockScope getBlockScope(String id) {
        return this.blockTable.getEntry(id);
    }

    /**
     * Get the specified sub scope of the specified scope.
     *
     * @return the sub scope of the specified scope with the input id's, or null if no such scope or sub scope exists.
     */
    @Override
    public Scope getSubScope(String scopeId, String subScopeId) {
        BlockScope blockScope = this.blockTable.getEntry(scopeId);
        if (blockScope != null)
            return blockScope.getSubscope(subScopeId);
        else return null;
    }

    /**
     * Evaluates a node, and return the appropriate scope id for it to open.
     * @param node The node which started the openScope method.
     * @return The name of the scope to open.
     */
    private String getScopeNameFromNode(NamedNode node) {
        switch (node.getNodeEnum()) {
            case CHANNEL_DECLARATIONS:
                return BlockScope.CHANNELS;

            case BLUEPRINT: 
                return BlockScope.BLUEPRINT;

            case PROCEDURE:
                String nodeId = ((NamedIdNode) node).getId();
                return BlockScope.PROCEDURE_PREFIX + nodeId;

            default:
                throw new IllegalArgumentException("Unexpected node.");
        }
    }

    /**
     * Inserts a variable into the most recent scope
     *
     * @param node The new node to insert into the symbol table.
     */
    @Override
    public void insertVariable(NamedNode node) {
        this.blockTable.getLatest().getLatestSubScope().setVariable((NamedIdNode) node);
    }

    /**
     * Updates a variable by reassigning it. This is triggered by assigning to an already declared variable.
     *
     * @param assignNode The variable to reassign.
     */
    @Override
    public void reassignVariable(NamedNode assignNode) {
        NamedIdNode leftSide = (NamedIdNode) assignNode.getChild();
        NamedNode rightSide = (NamedNode) leftSide.getSib();

        String leftSideId = leftSide.getId();

        VariableEntry variableEntry =
                this.blockTable.getLatest().getScope()       // Get latest block scope
                        .getLatest().getVariable(leftSideId);    // Get latest subscope and the specific variable

        if (variableEntry == null){
            throw new NoSuchVariableDeclaredException("The right side of this assignment was not declared: " + assignNode);
        }

        switch (rightSide.getNodeEnum()) {
            case DRAW:
            case BUILD:
            case SELECTOR:
            case SIZE:
                variableEntry
                        .setSubType(((NamedIdNode) rightSide));    // Set the subtype of that variable.
                break;
            default:
                throw new IllegalArgumentException("Unexpected node.");
        }
    }

    /**
     * Returns the latest block scope
     *
     * @return the most recently added block scope, or Null if no such blockscope exists.
     */
    @Override
    public BlockScope getLatestBlockScope() {
        return this.blockTable.getLatest();
    }



    /**
     * The to string method.
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return AnsiColor.CYAN.toString()
                + AnsiColor.FONT_UNDERLINE.toString()
                + AnsiColor.FONT_BOLD
                + "Symbol Table:" + AnsiColor.RESET + "\n\n" + this.blockTable.toString();
    }
}
