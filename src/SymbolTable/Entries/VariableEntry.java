package SymbolTable.Entries;

import SymbolTable.Exceptions.InvalidEntryCastingException;
import SymbolTable.SymbolType;

public class VariableEntry implements TableEntry {

    private String id;
    private SymbolType symbolType;

    public VariableEntry(String id, SymbolType symbolType) {
        this.id = id;
        this.symbolType = symbolType;
    }

    public String getId() {
        return id;
    }

    public SymbolType getSymbolType() {
        return symbolType;
    }

    @Override
    public ScopeEntry specifyAsScopeEntry() throws InvalidEntryCastingException {
        throw new InvalidEntryCastingException("Can not specify a VariableEntry as a ScopeEntry");
    }

    @Override
    public VariableEntry specifyAsVariableEntry() {
        return this;
    }
}
