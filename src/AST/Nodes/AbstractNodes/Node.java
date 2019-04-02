package AST.Nodes.AbstractNodes;

import AST.Nodes.AbstractNodes.Nodes.AbstractNode;
import AST.Visitor;

/**
 * Contains essential functionality of an AST node for the parser.
 */
public interface Node {

    /** Join the end of this sibling's list with the supplied sibling's list
     * @param sib The sibling to add to the end of the list.
     * @return
     */
    AbstractNode makeSibling(AbstractNode sib);

    AbstractNode adoptChildren(AbstractNode n);

    AbstractNode adoptChildren(AbstractNode... nodes);

    AbstractNode adoptAsFirstChild(AbstractNode node);

    AbstractNode orphan();

    AbstractNode abandonChildren();

    AbstractNode getParent();

    AbstractNode getSib();

    AbstractNode getChild();

    AbstractNode getFirstSib();

    String getName();

    int getNodeNum();

    void walkTree(Visitor v);

    AbstractNode[] getNodes();
}
