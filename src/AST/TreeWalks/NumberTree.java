package AST.TreeWalks;

import AST.Nodes.AbstractNodes.Nodes.AbstractNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNode;
import AST.Visitor;

public class NumberTree implements Visitor {
    private int counter = 0;

    /**
     * @param printLevel   the level, used connect decide how many indents there should be in the forward statement.
     * @param abstractNode The node which is being visited.
     */
    @Override
    public void pre(int printLevel, AbstractNode abstractNode) {
        NumberedNode node = (NumberedNode) abstractNode;
        node.setNumber(counter++);
    }

    /**
     * @param printLevel   the level, used connect decide how many indents there should be in the forward statement.
     * @param abstractNode The node which is being visited.
     */
    @Override
    public void post(int printLevel, AbstractNode abstractNode) {
        /* DoNothing(); */
    }
}
