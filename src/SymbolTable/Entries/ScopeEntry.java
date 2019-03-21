package SymbolTable.Entries;

import AST.AbstractNode;
import SymbolTable.Exceptions.InvalidEntryCastingException;
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

    public AbstractNode getNode() {
        return node;
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
