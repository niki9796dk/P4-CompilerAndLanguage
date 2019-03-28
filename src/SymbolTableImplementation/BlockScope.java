package SymbolTableImplementation;

import AST.Nodes.AbstractNodes.Nodes.AbstractNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNode;
import Enums.AnsiColor;

public class BlockScope {
    public static final String PROCEDURE_PREFIX = "PROC_";
    public static final String CHANNELS         = "ChannelDeclaration";
    public static final String BLUEPRINT        = "Blueprint";

    private String id;
    private NamedTable<Scope> scope;
    private AbstractNode node;

    public BlockScope(String id, AbstractNode node) {
        this.id = id;
        this.scope = new NamedTable<>();
        this.node = node;
    }

    public String getId() {
        return id;
    }

    public NamedTable<Scope> getScope() {
        return this.scope;
    }

    public AbstractNode getNode() {
        return node;
    }

    public void openScope(String id, NamedNode node) {
        this.getScope().setEntry(id, new Scope(id, node));
    }

    public Scope getLatestSubScope() {
        return this.getScope().getLatest();
    }

    public Scope getChannelDeclarationScope() {
        return this.getScope().getEntry(CHANNELS);
    }

    public Scope getBlueprintScope() {
        return this.getScope().getEntry(BLUEPRINT);
    }

    public Scope getProcedureScope(String id) {
        return this.getScope().getEntry(PROCEDURE_PREFIX + id);
    }

    @Override
    public String toString() {
        return AnsiColor.GREEN + "BlockScope " + this.id + AnsiColor.RESET + ":\n" + this.getScope().toString();
    }
}
