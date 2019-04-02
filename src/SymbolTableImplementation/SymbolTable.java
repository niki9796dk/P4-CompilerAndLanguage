package SymbolTableImplementation;

import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;
import Enums.AnsiColor;
import SymbolTableImplementation.Exceptions.EmptySymboltableException;

import java.util.Arrays;
import java.util.HashSet;

public class SymbolTable implements SymbolTableInterface {
    private NamedTable<BlockScope> blockTable = new NamedTable<>();

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

    @Override
    public void openBlockScope(NamedNode node) {
        NamedIdNode blockIdNode = ((NamedIdNode) node);
        String blockId = blockIdNode.getId();

        this.blockTable.setEntry(blockId, new BlockScope(blockId, blockIdNode));
    }

    @Override
    public void openSubScope(NamedNode node) {

        String scopeName = this.getScopeNameFromNode(node);

        this.blockTable.getLatest().openScope(scopeName, node);
    }

    @Override
    public BlockScope getBlockScope(String id) {
        return this.blockTable.getEntry(id);
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

    @Override
    public void insertVariable(NamedNode node) {
        this.blockTable.getLatest().getLatestSubScope().setVariable((NamedIdNode) node);
    }

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
                variableEntry.setSubType(((NamedIdNode) rightSide));    // Set the subtype of that variable.
                break;

            case SIZE:
                // Do nothing.
                break;

            default:
                throw new IllegalArgumentException("Unexpected node.");
        }
    }

    @Override
    public BlockScope getLatestBlockScope() {
        BlockScope b = this.blockTable.getLatest();
        if (b == null)
            throw new EmptySymboltableException();
        return this.blockTable.getLatest();
    }

    @Override
    public boolean isPredefinedOperation(String operation) {
        return OPERATIONS.contains(operation);
    }

    @Override
    public String toString() {
        return AnsiColor.CYAN.toString()
                + AnsiColor.FONT_UNDERLINE.toString()
                + AnsiColor.FONT_BOLD
                + "Symbol Table:" + AnsiColor.RESET + "\n\n" + this.blockTable.toString();
    }
}
