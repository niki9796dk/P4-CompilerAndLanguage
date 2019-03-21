package SymbolTable.Tables;

import AST.AbstractNode;
import SymbolTable.Entries.ScopeEntry;

import java.util.HashMap;

public class RootTable implements Table{

    private HashMap<String, ScopeEntry> table;
    private ScopeEntry newEntry;

    public RootTable() {
        this.table = new HashMap<>();
    }

    public void setNewEntry(String id, AbstractNode node) {
        ScopeEntry entry = new ScopeEntry(id, new BlockTable(), node);
        this.table.put(id, entry);
        this.newEntry = entry;
    }

    public ScopeEntry getNewEntry() {
        return this.newEntry;
    }
}
