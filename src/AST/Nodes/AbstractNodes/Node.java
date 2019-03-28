package AST.Nodes.AbstractNodes;

import AST.Visitor;

interface Node {

    AbstractNode makeSibling(AbstractNode sib);

    /**
     * Adopt the supplied node and all of its siblings under this node
     */
    AbstractNode adoptChildren(AbstractNode n);

    AbstractNode adoptChildren(AbstractNode... nodes);

    // Insert child in the front of the child list
    AbstractNode adoptAsFirstChild(AbstractNode node);

    AbstractNode orphan();

    AbstractNode abandonChildren();


    AbstractNode getParent();

    AbstractNode getSib();

    AbstractNode getChild();

    AbstractNode getFirst();

    String getName();

    int getNodeNum();

    void walkTree(Visitor v);

    AbstractNode[] getNodes();
}
