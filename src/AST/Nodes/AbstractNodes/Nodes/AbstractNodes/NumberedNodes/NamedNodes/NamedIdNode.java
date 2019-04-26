package AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNode;

/**
 * A node with a name and Id, both as strings.
 */
public abstract class NamedIdNode extends NamedNode {
    /**
     * The id of the node as a string.
     */
    private String id;

    /**
     * Construct a node with a name, id, and an appropriate class enum.
     * @param name The name of the node.
     * @param id The unique id of the node.
     * @param nodeEnum An enum appropriate for a non-abstract class extending this class.
     */
    public NamedIdNode(String name, String id, NodeEnum nodeEnum) {
        super(name, nodeEnum);
        this.id = id;
    }

    /**
     * Get the unique id of the node as a string.
     * @return The id as a string.
     */
    public String getId() {
        return id;
    }

    /**
     * Set the unique id of the node using a string.
     * @param id What to set the unique ID as using a string.
     * @return  a reference to this object.
     */
    public AbstractNode setId(String id) {
        this.id = id;
        return this;
    }

    @Override
    public String toString() {
        return super.toString() + " " + this.id;
    }
}
