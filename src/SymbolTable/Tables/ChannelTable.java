package SymbolTable.Tables;

import SymbolTable.Entries.VariableEntry;
import SymbolTable.Entries.EntryContents.SymbolType;

import java.util.HashMap;

public class ChannelTable implements Table {

    private HashMap<String, VariableEntry> table;
    private VariableEntry newEntry;

    public ChannelTable() {
        this.table = new HashMap<>();
    }

    public void setNewEntry(String id, SymbolType symbolType) {
        VariableEntry entry = new VariableEntry(id, symbolType);
        this.table.put(id, entry);
        this.newEntry = entry;
    }

    public VariableEntry getNewEntry() {
        return this.newEntry;
    }
}
