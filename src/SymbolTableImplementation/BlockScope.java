package SymbolTableImplementation;

import AST.Nodes.AbstractNodes.Nodes.AbstractNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.BlockNode;
import Enums.AnsiColor;

/**
 *
 */
public class BlockScope {
    /**
     * The prefix expected for procedure subscopes.
     */
    public static final String PROCEDURE_PREFIX = "PROC_";
    /**
     * The id of the Channel Declaration scope.
     */
    public static final String CHANNELS = "ChannelDeclaration";
    /**
     * The id of the Blueprint scope.
     */
    public static final String BLUEPRINT = "Blueprint";

    private String id;
    private NamedTable<Scope> scope;
    private BlockNode node;

    /**
     * Create a BlockScope with a unique Id for a specific node. Created with empty scope contents.
     * @param id   The identifying ID of the block scope.
     * @param node The BlockNode which this Blockscope will contain the scope of.
     */
    public BlockScope(String id, BlockNode node) {
        this.id = id;
        this.scope = new NamedTable<>();
        this.node = node;
    }

    /**
     * @return Returns the unique Id identifier of the blockscope.
     */
    public String getId() {
        return id;
    }

    /**
     * @return Returns the NamedTable containing all the scopes within the block.
     */
    public NamedTable<Scope> getScope() {
        return this.scope;
    }

    /**
     * @return Returns the BlockNode of which the object is a scope of.
     */
    public AbstractNode getNode() {
        return node;
    }

    /**
     * This will open a new scope within the BlockScope, therefore adding it to the scopes of the object.
     * @param id The unique identifier of the new scope. It will only be retrievable if it follows the id naming conventions of the blockscope.
     * @param node The node which the new scope is a scope of.
     */
    public void openScope(String id, NamedNode node) {
        this.getScope().setEntry(id, new Scope(id, node));
    }

    /**
     * @return The most recently opened scope, or Null if the blockscope is empty.
     */
    public Scope getLatestSubScope() {
        return this.getScope().getLatest();
    }

    /**
     * @return The scope of the channel declarations.
     */
    public Scope getChannelDeclarationScope() {
        return this.getScope().getEntry(CHANNELS);
    }

    /**
     * @return The scope of the blocks blueprint.
     */
    public Scope getBlueprintScope() {
        return this.getScope().getEntry(BLUEPRINT);
    }

    /**
     * @param id The name of the procedure.
     * @return The scope of the procedure, or Null if no such procedure exists.
     */
    public Scope getProcedureScope(String id) {
        return this.getScope().getEntry(PROCEDURE_PREFIX + id);
    }

    @Override
    public String toString() {
        return AnsiColor.GREEN + "BlockScope " + this.id + AnsiColor.RESET + ":\n" + this.getScope().toString();
    }
}
