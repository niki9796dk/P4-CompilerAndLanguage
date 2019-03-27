package SymbolTable.Tables;

import AST.Nodes.AbstractNodes.AbstractNode;
import SymbolTable.Entries.BlockEntry;

import java.util.HashMap;

public class RootTable implements Table{

    private HashMap<String, BlockEntry> table;
    private BlockEntry newEntry;

    public RootTable() {
        this.table = new HashMap<>();
    }

    public void setNewEntry(String id, AbstractNode node) {
        BlockEntry entry = new BlockEntry(id, new BlockTable(), node);
        this.table.put(id, entry);
        this.newEntry = entry;
    }

    public BlockEntry getNewEntry() {
        System.out.println("New entry: " + this.newEntry.toString());
        return this.newEntry;
    }

    public ChannelTable getChannelScope() {
        return this.getNewEntry().getScope().getChannelScope();
    }
}
