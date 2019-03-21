package SymbolTable.Tables;

import AST.AbstractNode;
import SymbolTable.Entries.BlockEntry;
import SymbolTable.Entries.ScopeEntry;

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
        entry.getScope().setChannelScope("Channels");
        entry.getScope().setBlueprintscope("Blueprint");
    }

    public BlockEntry getNewEntry() {
        System.out.println("New entry: ");
        return this.newEntry;
    }

    public ChannelTable getChannelScope() {
        return this.getNewEntry().getScope().getChannelScope();
    }
}
