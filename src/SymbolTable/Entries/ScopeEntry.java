package SymbolTable.Entries;

import SymbolTable.Exceptions.InvalidEntryCastingException;
import SymbolTable.Tables.BlueprintTable;
import SymbolTable.Tables.ChannelTable;
import SymbolTable.Tables.ProcedureTable;
import SymbolTable.Tables.Table;

public class ScopeEntry implements TableEntry{

    private String id;
    private Table scope;

    public ScopeEntry(String id, Table scope) {
        this.id = id;
        this.scope = scope;
    }

    public String getId() {
        return id;
    }

    public ChannelTable getChannelScope() {
        return (ChannelTable) scope;
    }

    public BlueprintTable getBlueprintScope() {
        return (BlueprintTable) scope;
    }

    public ProcedureTable getProcedureScope() {
        return (ProcedureTable) scope;
    }

    @Override
    public ScopeEntry specifyAsScopeEntry() {
        return this;
    }

    @Override
    public VariableEntry specifyAsVariableEntry() throws InvalidEntryCastingException {
        throw new InvalidEntryCastingException("Can not specify a ScopeEntry as a VariableEntry");
    }
}
