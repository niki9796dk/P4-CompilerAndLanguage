package SymbolTableImplementation;

import AST.Nodes.AbstractNodes.Nodes.AbstractNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNode;
import Enums.AnsiColor;

/**
 *
 */
public class BlockScope {
    public static final String PROCEDURE_PREFIX = "PROC_";
    public static final String CHANNELS         = "ChannelDeclaration";
    public static final String BLUEPRINT        = "Blueprint";

    private String id;
    private NamedTable<Scope> scope;
    private AbstractNode node;

    /**
     * @param id The identifying ID of the block scope.
     * @param node The node which
     */
    public BlockScope(String id, AbstractNode node) {
        this.id = id;
        this.scope = new NamedTable<>();
        this.node = node;
    }

    /**
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * @return
     */
    public NamedTable<Scope> getScope() {
        return this.scope;
    }

    /**
     * @return
     */
    public AbstractNode getNode() {
        return node;
    }

    /**
     * @param id
     * @param node
     */
    public void openScope(String id, NamedNode node) {
        this.getScope().setEntry(id, new Scope(id, node));
    }

    /**
     * @return
     */
    public Scope getLatestSubScope() {
        return this.getScope().getLatest();
    }

    /**
     * @return
     */
    public Scope getChannelDeclarationScope() {
        return this.getScope().getEntry(CHANNELS);
    }

    /**
     * @return
     */
    public Scope getBlueprintScope() {
        return this.getScope().getEntry(BLUEPRINT);
    }

    /**
     * @param id
     * @return
     */
    public Scope getProcedureScope(String id) {
        return this.getScope().getEntry(PROCEDURE_PREFIX + id);
    }

    @Override
    public String toString() {
        return AnsiColor.GREEN + "BlockScope " + this.id + AnsiColor.RESET + ":\n" + this.getScope().toString();
    }
}
