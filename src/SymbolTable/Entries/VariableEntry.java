package SymbolTable.Entries;

import AST.Nodes.AbstractNodes.AbstractNode;
import SymbolTable.Entries.EntryContents.SymbolType;
import SymbolTable.Exceptions.InvalidEntryCastingException;

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

    @Override
    public ScopeEntry specifyAsScopeEntry() throws InvalidEntryCastingException {
        throw new InvalidEntryCastingException("Can not specify a VariableEntry as a ScopeEntry");
    }

    @Override
    public VariableEntry specifyAsVariableEntry() {
        return this;
    }
}
