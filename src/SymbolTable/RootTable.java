package SymbolTable;

import AST.AbstractNode;

import java.util.HashMap;
import java.util.Map;

public class RootTable implements Table{

    private HashMap<String, ScopeEntry> table;
    private ScopeEntry newItem;

    public RootTable() {
        this.table = new HashMap<>();
    }

    public void setNewItem(String id, AbstractNode node) {
        ScopeEntry entry = new ScopeEntry(id, new BlockTable(), node);
        this.table.put(id, entry);
        this.newItem = entry;
    }

    public ScopeEntry getNewItem() {
        return this.newItem;
    }
}
