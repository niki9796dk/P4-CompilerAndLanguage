package AST.Nodes.AbstractNodes;

import AST.Enums.NodeEnum;

/**
 * An interface specifying a nodes ability to return an Enum corresponding to the type of the node.
 * Useful for easy implementation of switch statements depending on the class of a node.
 */
public interface NodeEnumAble {
    /**
     * @return an enum corresponding to the nodes class.
     */
    NodeEnum getNodeEnum();
}
