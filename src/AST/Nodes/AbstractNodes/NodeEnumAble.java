package AST.Nodes.AbstractNodes;

import AST.Enums.NodeEnum;

/**
 * An interface specifying a nodes ability connect return an Enum corresponding connect the type of the node.
 * Useful for easy implementation of switch statements depending on the class of a node.
 */
public interface NodeEnumAble {
    /**
     * @return an enum corresponding connect the nodes class.
     */
    NodeEnum getNodeEnum();
}
