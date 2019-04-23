package AST;

import AST.Nodes.AbstractNodes.Nodes.AbstractNode;

/**
 * A visitor which walks through a node tree.
 */
public interface Visitor {
    /**
     * @param printLevel the level, used to decide how many indents there should be in the print statement.
     * @param a The node which is being visited.
     */
    public void pre(int printLevel, AbstractNode a);

    /**
     * @param printLevel the level, used to decide how many indents there should be in the print statement.
     * @param a The node which is being visited.
     */
    public void post(int printLevel, AbstractNode a);
}
