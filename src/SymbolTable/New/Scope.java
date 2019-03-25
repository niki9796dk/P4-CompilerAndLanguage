package SymbolTable.New;

import AST.Nodes.AbstractNodes.AbstractNode;
import AST.Nodes.AbstractNodes.NamedIdNode;
import AST.Nodes.AbstractNodes.NamedNode;
import SymbolTable.Entries.ScopeEntry;
import SymbolTable.Entries.TableEntry;
import SymbolTable.Entries.VariableEntry;
import SymbolTable.Exceptions.InvalidEntryCastingException;
import SymbolTable.Tables.BlockTable;

public class Scope {
    private String id;
    private NamedTable<SymbolTable.New.VariableEntry> scope;
    private AbstractNode node;

    public Scope(String id, NamedNode node) {
        this.id = id;
        this.node = node;
        this.scope = new NamedTable<>();
    }

    public String getId() {
        return id;
    }

    public void setVariable(NamedIdNode node) {
        this.scope.setEntry(node.getId(), new SymbolTable.New.VariableEntry(node));
    }

    public SymbolTable.New.VariableEntry getVariable(String id) {
        return this.scope.getEntry(id);
    }

    public AbstractNode getNode() {
        return node;
    }

    @Override
    public String toString() {
        return "\t\tScope " + id + ": \n\t\t\t" + this.scope.toString();
    }
}
