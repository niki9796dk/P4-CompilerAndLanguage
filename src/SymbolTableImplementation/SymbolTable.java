package SymbolTableImplementation;

import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.BlockNode;
import Enums.AnsiColor;

import java.util.Arrays;
import java.util.HashSet;

/**
 * An implementation of a SymbolTable, which contains the scopes of blocks.
 */
public class SymbolTable implements SymbolTableInterface {
    /**
     *
     */
    private NamedTable<BlockScope> blockTable = new NamedTable<>();

    /**
     * Constructs an empty {@code SymbolTable} with the default initial capacity
     */
    public SymbolTable() {
    }

    // List of predefined operations
    private static final HashSet<String> OPERATIONS = new HashSet<>(Arrays.asList(
            // Matrix arithmetic operations
            "Addition", "Multiplication", "Subtraction",
            // Unitwise Arithmetic operations
            "_Addition", "_Multiplication", "_Subtraction", "_Division",
            // Activation functions
            "_Sigmoid", "_Tanh", "_Relu",
            // Matrix operations
            "Transpose"));
    // Predefined Sources
    private static final HashSet<String> SOURCES = new HashSet<>(Arrays.asList(
            "Source", "FixedSource"));

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
     * @param id The id of the desired blockscope.
     * @return The blockscope with the id, or Null if no such blockscope exists.
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
    public Scope getSubScope(String scopeId, String subscopeId) {
        BlockScope blockScope = this.blockTable.getEntry(scopeId);
        if (blockScope != null)
            return blockScope.getSubscope(subscopeId);
        else return null;
    }

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

        switch (rightSide.getNodeEnum()) {
            case DRAW:
            case BUILD:
            case SELECTOR:
                variableEntry
                        .setSubType(((NamedIdNode) rightSide));    // Set the subtype of that variable.
                break;

            case SIZE:
                // Do nothing.
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
     * Check if an operation keyword is one of the final predefined operations.
     *
     * @param operation The operation Keyword
     * @return whether the operation is valid.
     */
    @Override
    public boolean isPredefinedOperation(String operation) {
        return OPERATIONS.contains(operation);
    }

    /**
     * Check if a source keyword is a one of the final predefined operations.
     *
     * @param source The source Keyword
     * @return whether the source is valid.
     */
    @Override
    public boolean isPredefinedSource(String source) {
        return SOURCES.contains(source);
    }

    @Override
    public String toString() {
        return AnsiColor.CYAN.toString()
                + AnsiColor.FONT_UNDERLINE.toString()
                + AnsiColor.FONT_BOLD
                + "Symbol Table:" + AnsiColor.RESET + "\n\n" + this.blockTable.toString();
    }
}
