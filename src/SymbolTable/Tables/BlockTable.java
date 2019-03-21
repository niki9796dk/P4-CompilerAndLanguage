package SymbolTable.Tables;

import AST.AbstractNode;
import SymbolTable.Entries.ScopeEntry;

import java.util.HashMap;

public class BlockTable implements Table {

    private HashMap<String, ScopeEntry> table;
    private ScopeEntry newEntry;

    public BlockTable() {
        this.table = new HashMap<>();
    }

    public void setChannelScope(String id) {
        ScopeEntry entry = new ScopeEntry(id, new ChannelTable());
        this.table.put(id, entry);
    }

    public void setBlueprintscope(String id) {
        ScopeEntry entry = new ScopeEntry(id, new ChannelTable());
        this.table.put(id, entry);
    }

    public void setNewProcedureScope(String id) {
        ScopeEntry entry = new ScopeEntry(id, new ProcedureTable());
        this.table.put(id, entry);
        this.newEntry = entry;
    }

    public ScopeEntry getNewEntry() {
        return this.newEntry;
    }

}
