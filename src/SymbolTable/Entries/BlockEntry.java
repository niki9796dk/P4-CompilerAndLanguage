package SymbolTable.Entries;

import AST.Nodes.AbstractNodes.AbstractNode;
import SymbolTable.Exceptions.InvalidEntryCastingException;
import SymbolTable.Tables.*;

public class BlockEntry implements TableEntry {
    private String id;
    private BlockTable scope;
    private AbstractNode node;

    public BlockEntry(String id, BlockTable scope, AbstractNode node) {
        this.id = id;
        this.scope = scope;
        this.node = node;
        this.getScope().setChannelScope();
        this.getScope().setBlueprintscope();
    }

    public String getId() {
        return id;
    }

    public BlockTable getScope() {
        return scope;
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
