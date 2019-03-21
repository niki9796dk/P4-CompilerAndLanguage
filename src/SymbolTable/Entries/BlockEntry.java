package SymbolTable.Entries;

import AST.AbstractNode;
import SymbolTable.Exceptions.InvalidEntryCastingException;
import SymbolTable.Tables.*;

public class BlockEntry implements TableEntry {
    private String id;
    private Table scope;
    private AbstractNode node;

    public BlockEntry(String id, Table scope, AbstractNode node) {
        this.id = id;
        this.scope = scope;
        this.node = node;
    }

    public String getId() {
        return id;
    }

    public BlockTable getScope() {
        return (BlockTable) scope;
    }

    public AbstractNode getNode() {
        return node;
    }

    @Override
    public ScopeEntry specifyAsScopeEntry() throws InvalidEntryCastingException {
        return null;
    }

    @Override
    public VariableEntry specifyAsVariableEntry() throws InvalidEntryCastingException {
        return null;
    }
}
