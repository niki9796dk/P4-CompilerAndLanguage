package SymbolTable;

import AST.Nodes.AbstractNodes.Nodes.AbstractNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNode;
import Enums.AnsiColor;

public class Scope {
    private String id;
    private NamedTable<VariableEntry> scope;
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
        this.scope.setEntry(node.getId(), new VariableEntry(node));
    }

    public VariableEntry getVariable(String id) {
        return this.scope.getEntry(id);
    }

    public AbstractNode getNode() {
        return node;
    }

    @Override
    public String toString() {
        return AnsiColor.BLUE + "\tSubScope " + id + AnsiColor.RESET + ": \n" + this.scope.toString();
    }
}
