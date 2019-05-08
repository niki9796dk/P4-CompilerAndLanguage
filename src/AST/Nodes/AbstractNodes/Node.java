package AST.Nodes.AbstractNodes;

import AST.Nodes.AbstractNodes.Nodes.AbstractNode;
import AST.Visitor;

/**
 * Contains essential functionality of an AST node for the parser.
 */
public interface Node {

    /** Join the end of this sibling's list with the supplied sibling's list
     * @param sib The sibling to add to the end of the list.
     * @return The last sibling of the input parameter "sib"
     */
    AbstractNode makeSibling(AbstractNode sib);

    /**
     * Adopt the supplied node and all of its siblings under this node
     * @param n The node to adopt.
     * @return  a reference to this object.
     */
    AbstractNode adoptChildren(AbstractNode n);

    /**
     * Adopt the supplied nodes and all of their siblings under this node
     * @param nodes An array of nodes to adopt.
     * @return  a reference to this object.
     */
    AbstractNode adoptChildren(AbstractNode... nodes);

    /**
     * Insert child in the front of the child list
     * @param node The child to add.
     * @return  a reference to this object.
     */
    AbstractNode adoptAsFirstChild(AbstractNode node);

    /**
     * Turn the child into an orphan.
     * @return  a reference to this object.
     */
    AbstractNode orphan();

    /**
     * Nullify the reference to the child node.
     * @return  a reference to this object.
     */
    AbstractNode abandonChildren();

    /**
     * Get the parent of the node.
     * @return parent of the node.
     */
    AbstractNode getParent();

    /**
     * Get the sibling of the node.
     * @return sibling of the node.
     */
    AbstractNode getSib();

    /**
     * Get the child of the node.
     * @return the child of the node.
     */
    AbstractNode getChild();

    /**
     * Get the first child of a node, of a given class
     * @param childClass The expected child class
     * @return First child of class 'childClass'.
     */
    <T> T findFirstChildOfClass(Class<T> childClass);

    /**
     * Counts the child nodes
     * @return An integer value, representing the amount of child nodes.
     */
    int countChildren();

    /**
     * Counts the amount of children of a specific class.
     * @param childClass The children class which is to be counted.
     * @return The amount of children of the specified class.
     */
    int countChildrenInstanceOf(Class childClass);

    /**
     * Get the first sibling of the node.
     * @return the first sibling of the node.
     */
    AbstractNode getFirstSib();

    /**
     * Walk the tree of child nodes using a visitor.
     * @param v the visitor object to use.
     */
    void walkTree(Visitor v);
}
