package AST.Nodes.AbstractNodes.Nodes.AbstractNodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNode;

/**
 * This node can optionally be labelled with a specified number.
 *
 */
public abstract class NumberedNode extends AbstractNode {
    /**
     * The number label of the node. If unspecified, it is -1.
     */
    private int number = -1;

    /**
     *
     */
    private NodeEnum nodeEnum;

    /**
     * @param number
     * @param nodeEnum
     */
    public NumberedNode(int number, NodeEnum nodeEnum) {
        this(nodeEnum);
        this.setNumber(number);
    }

    /**
     * @param nodeEnum
     */
    public NumberedNode(NodeEnum nodeEnum) {
        this.nodeEnum = nodeEnum;
    }

    /**
     * @return
     */
    public int getNumber() {
        return number;
    }

    /**
     * @return
     */
    public NodeEnum getNodeEnum() {
        return nodeEnum;
    }

    /**
     * @param number
     */
    public void setNumber(int number) {
        if(number < 0)
            throw new IllegalArgumentException("Number given to node is " + number + ", but it must be a non-negative integer.");
        this.number = number;
    }

    @Override
    public String toString() {
        return "[" + this.number + "]";
    }
}
