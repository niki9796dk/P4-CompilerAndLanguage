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
     *
     * @param name
     * @param id
     * @param nodeEnum
     */
    public NamedIdNode(String name, String id, NodeEnum nodeEnum) {
        super(name, nodeEnum);
        this.id = id;
    }

    /**
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     * @return
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
