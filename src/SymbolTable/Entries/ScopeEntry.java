package SymbolTable.Entries;

import AST.AbstractNode;
import SymbolTable.Tables.Table;

public class ScopeEntry implements TableEntry{

    private String id;
    private Table scope;
    private AbstractNode node;

    public ScopeEntry(String id, Table scope, AbstractNode node) {
        this.id = id;
        this.scope = scope;
        this.node = node;
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
}
