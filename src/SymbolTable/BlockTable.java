package SymbolTable;

import AST.AbstractNode;

import java.util.HashMap;

public class BlockTable implements Table {

    private HashMap<String, ScopeEntry> table;
    private ScopeEntry newProcedure;

    public BlockTable() {
        this.table = new HashMap<>();
    }

    public void setChannelScope(String id, AbstractNode node) {
        ScopeEntry entry = new ScopeEntry(id, new ChannelTable(), node);
        this.table.put(id, entry);
    }

    public void setBlueprintscope(String id, AbstractNode node) {
        ScopeEntry entry = new ScopeEntry(id, new ChannelTable(), node);
        this.table.put(id, entry);
    }

    public void setNewProcedureScope(String id, AbstractNode node) {
        ScopeEntry entry = new ScopeEntry(id, new ProcedureTable(), node);
        this.table.put(id, entry);
        this.newProcedure = entry;
    }

    public ScopeEntry getNewProcedure() {
        return this.newProcedure;
    }

}
