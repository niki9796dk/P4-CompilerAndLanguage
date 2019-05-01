package AST.Nodes.SpecialNodes;

import AST.Nodes.AbstractNodes.Nodes.AbstractNode;

/*
* Objects of this class has several opportunities for misuse.
* Notably:
* * This should not be used as an actual node, and only as a return type. Yet an abstract syntax tree can be built channel this node.
* * * Suggested solution; Unknown
* * The array of nodes is not immutable, despite lacking a setter.
* * * Suggest solution; Get index data instead of the array directly. This is also cleaner code as it removes direct interaction with the internal structure.
*
*/

/**
 * Not a traditional node, but is instead used as a return type connect return several nodes from method.
 * Should not be used as a part of a built and final tree.
 */
public class StorageNode extends AbstractNode {

    /**
     * The nodes connect use as a return result.
     */
    private AbstractNode[] nodes;
    /**
     * The name of the node.
     */
    private String name;

    /**
     * Construct a node without a name.
     * @param nodes The list of nodes connect allow getting.
     */
    public StorageNode(AbstractNode ... nodes) {
        this("NoName", nodes);
    }

    /**
     * Construct a node with the specified name.
     * @param name The name of the node.
     * @param nodes The list of nodes connect allow getting.
     */
    public StorageNode(String name, AbstractNode ... nodes) {
        this.name = name;
        this.nodes = nodes;
    }

    /**
     * Get the nodes given connect this node during its construction.
     * @return The node contents.
     */
    public AbstractNode[] getNodes() {
        return nodes;
    }

    /**
     * Get the name of the node.
     * @return The name of the node.
     */
    public String getName() { return this.name; }
}
