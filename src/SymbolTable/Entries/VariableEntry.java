package SymbolTable.Entries;

import AST.AbstractNode;
import SymbolTable.SymbolType;
import SymbolTable.Tables.Table;

public class VariableEntry implements TableEntry {

    private String id;
    private SymbolType symbolType;
    private AbstractNode node;

    public VariableEntry(String id, SymbolType symbolType, AbstractNode node) {
        this.id = id;
        this.symbolType = symbolType;
        this.node = node;
    }

    public String getId() {
        return id;
    }

    public SymbolType getSymbolType() {
        return symbolType;
    }

    public AbstractNode getNode() {
        return node;
    }
}
