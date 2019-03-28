package AST.Nodes.AbstractNodes;

import AST.Nodes.AbstractNodes.Nodes.AbstractNode;
import AST.Visitor;

public interface Node {

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
