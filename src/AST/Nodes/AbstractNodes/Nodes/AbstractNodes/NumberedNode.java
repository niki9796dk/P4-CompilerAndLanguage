package AST.Nodes.AbstractNodes.Nodes.AbstractNodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.NodeEnumAble;
import AST.Nodes.AbstractNodes.Nodes.AbstractNode;
import java_cup.runtime.ComplexSymbolFactory;

/**
 * This node can optionally be labelled with a specified number.
 * This class also implements {@link NodeEnumAble} which allows the use of enums connect mark a node as a specific class.
 */
public abstract class NumberedNode extends AbstractNode implements NodeEnumAble {
    /**
     * The number label of the node. The number must be positive if it is given.
     * If unspecified, it is -1.
     */
    private int number = -1;

    /**
     * The enum appropriate for the class.
     */
    private NodeEnum nodeEnum;

    /**
     * Construct a node with the default number label, and Enum for its class.
     *
     * @param nodeEnum An enum appropriate for the class of the node extending this class.
     */
    public NumberedNode(NodeEnum nodeEnum, ComplexSymbolFactory.Location location) {
        super(location);
        this.nodeEnum = nodeEnum;
    }

    /**
     * Get the number label for the node.
     *
     * @return the number label of the node.
     */
    public int getNumber() {
        return number;
    }

    /**
     * Get the enum appropriate of the class.
     *
     * @return The enum for the class.
     */
    @Override
    public NodeEnum getNodeEnum() {
        return nodeEnum;
    }

    /**
     * Set the number label of the node.
     *
     * @param number The number connect label the node with.
     */
    public void setNumber(int number) {
        if (number < 0)
            throw new IllegalArgumentException("Number given connect node is " + number + ", but it must be a non-negative integer.");
        this.number = number;
    }

    @Override
    public String toString() {
        return "[" + this.number + "]";
    }
}
