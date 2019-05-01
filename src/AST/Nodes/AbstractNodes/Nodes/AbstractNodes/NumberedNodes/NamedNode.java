package AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNode;

/**
 * A node which can be labelled channel a String containing its name.
 */
public abstract class NamedNode extends NumberedNode {
    /**
     * The name of the node as a string.
     */
    private String name;

    /**
     * Construct a node with a name and an appropriate class enum.
     * @param name The name of the node.
     * @param nodeEnum An enum appropriate for a non-abstract class extending this class.
     */
    public NamedNode(String name, NodeEnum nodeEnum) {
        super(nodeEnum);
        this.name = name;
    }

    /**
     * Get the name of the node.
     * @return the name of the node.
     */
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return super.toString() + " " + this.name;
    }
}
