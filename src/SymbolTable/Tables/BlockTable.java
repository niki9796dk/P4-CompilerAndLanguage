package SymbolTable.Tables;

import SymbolTable.Entries.ScopeEntry;

import java.util.HashMap;

public class BlockTable implements Table {

    private HashMap<String, ScopeEntry> table;
    private ScopeEntry newEntry;

    // Constants
    private static final String CHANNELS = "Channels";
    private static final String BLUEPRINT = "Blueprint";
    private static final String PROCEDURES = "Procedures";

    public BlockTable() {
        this.table = new HashMap<>();
    }

    public void setChannelScope() {
        ScopeEntry entry = new ScopeEntry(CHANNELS, new ChannelTable());
        this.table.put(CHANNELS, entry);
    }

    public void setBlueprintscope() {
        ScopeEntry entry = new ScopeEntry(BLUEPRINT, new ChannelTable());
        this.table.put(BLUEPRINT, entry);
    }

    public void setProcedureScope() {
        ScopeEntry entry = new ScopeEntry(PROCEDURES, new ProcedureTable());
        this.table.put(PROCEDURES, entry);
        this.newEntry = entry;
    }

    public ChannelTable getChannelScope() {
    return this.table.get(CHANNELS).getChannelScope();
    }

    public BlueprintTable getBlueprintScope() {
        return this.table.get(BLUEPRINT).getBlueprintScope();
    }

    public ScopeEntry getNewEntry() {
        return this.newEntry;
    }

}
