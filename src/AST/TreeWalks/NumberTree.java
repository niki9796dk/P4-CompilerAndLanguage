package AST.TreeWalks;

import AST.Nodes.AbstractNodes.Nodes.AbstractNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNode;
import AST.Visitor;

public class NumberTree implements Visitor {
    private int counter = 0;

    @Override
    public void pre(int printLevel, AbstractNode a) {
        NumberedNode node = (NumberedNode) a;
        node.setNumber(counter++);
    }

    @Override
    public void post(int printLevel, AbstractNode a) {
        // Do nothing...
    }
}
