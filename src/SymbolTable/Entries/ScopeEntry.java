package SymbolTable.Entries;

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

    public Table getScope() {
        return scope;
    }
}
