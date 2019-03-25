package SymbolTable.Tables;

import AST.Nodes.AbstractNodes.AbstractNode;
import SymbolTable.Entries.VariableEntry;
import SymbolTable.Entries.EntryContents.SymbolType;

import java.util.HashMap;

public class ChannelTable implements Table {

    private HashMap<String, VariableEntry> table;
    private VariableEntry newEntry;

    public ChannelTable() {
        this.table = new HashMap<>();
    }

    public void setNewEntry(String id, SymbolType symbolType, AbstractNode node) {
        VariableEntry entry = new VariableEntry(id, symbolType, node);
        this.table.put(id, entry);
        this.newEntry = entry;
    }

    public VariableEntry getNewEntry() {
        return this.newEntry;
    }
}
