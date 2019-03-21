package SymbolTable.Entries;

import AST.AbstractNode;
import SymbolTable.SymbolType;
import SymbolTable.Tables.Table;

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
}
