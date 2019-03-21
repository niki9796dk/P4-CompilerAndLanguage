package SymbolTable.Entries;

import SymbolTable.Exceptions.InvalidEntryCastingException;

public interface TableEntry {
    String getId();

    ScopeEntry specifyAsScopeEntry() throws InvalidEntryCastingException;

    VariableEntry specifyAsVariableEntry() throws InvalidEntryCastingException;
}
