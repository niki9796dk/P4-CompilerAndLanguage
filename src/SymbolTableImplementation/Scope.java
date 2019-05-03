package SymbolTableImplementation;

import AST.Nodes.AbstractNodes.Node;
import AST.Nodes.AbstractNodes.Nodes.AbstractNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;
import Enums.AnsiColor;

/**
 * Keeps track of what variables are within the scope of a node.
 */
public class Scope {
    private String id;
    private NamedTable<VariableEntry> scope;
    private AbstractNode node;

    /**
     * Creates a new scope with specified id and representing the specified node. The scope will be empty.
     *
     * @param id   The unique id identifier.
     * @param node The node of which the scope object is a scope of.
     */
    public Scope(String id, NamedNode node) {
        this.id = id;
        this.node = node;
        this.scope = new NamedTable<>();
    }

    /**
     * @return Returns the unique id of the scope.
     */
    public String getId() {
        return id;
    }

    /**
     * @param node Returns the node of which this is a scope of.
     */
    public void setVariable(NamedIdNode node) {
        this.scope.setEntry(node.getId(), new VariableEntry(node));
    }

    /**
     * @param node Returns the node of which this is a scope of.
     */
    public void setVariable(Node node) {
        if (node instanceof NamedIdNode)
            this.scope.setEntry(((NamedIdNode) node).getId(), new VariableEntry((NamedIdNode) node));
        else {
            throw new IllegalArgumentException("Input \"node\" must be subclass of NamedIdNode");
        }
    }

    /**
     * Retrieve a variable entry within the id
     *
     * @param id The id of the variable.
     * @return The variable entry with the specified id, or Null if no such entry exists.
     */
    public VariableEntry getVariable(String id) {
        return this.scope.getEntry(id);
    }

    /**
     * Retrieve a variable entry within the id of the input node.
     * @param node A namedIdNode
     * @return The variable entry with the specified id, or Null if no such entry exists.
     */
    public VariableEntry getVariable(NamedIdNode node) {
        return this.getVariable(node.getId());
    }

    /**
     * Retrieve a variable entry within the id of the input node. This method will typecast the node into a NamedIdNode.
     * @param node A node.
     * @return The variable entry with the specified id, or Null if no such entry exists.
     */
    public VariableEntry getVariable(Node node) {
        if (node instanceof NamedIdNode)
            return this.getVariable(((NamedIdNode) node).getId());
        throw new IllegalArgumentException("Input \"node\" must be subclass of NamedIdNode");
    }

    /**
     * @return The node of which the object is a scope of.
     */
    public AbstractNode getNode() {
        return node;
    }

    @Override
    public String toString() {
        return AnsiColor.BLUE + "\tSubScope " + id + AnsiColor.RESET + ": \n" + this.scope.toString();
    }
}
