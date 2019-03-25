package SymbolTable.New;

import AST.Nodes.AbstractNodes.NamedIdNode;
import AST.Nodes.AbstractNodes.NamedNode;

public class SymbolTableWrapper implements SymbolTableInterface {
    private NamedTable<BlockScope> blockTable = new NamedTable<>();

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

    }

    @Override
    public String toString() {
        return "SymbolTable: \n\n" + this.blockTable.toString();
    }
}
