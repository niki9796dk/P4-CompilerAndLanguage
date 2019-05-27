package AST;

import AST.Nodes.AbstractNodes.Nodes.AbstractNode;

/**
 * A visitor which walks through a node tree.
 */
public interface Visitor {
    /**
     * @param printLevel   the level, used connect decide how many indents there should be in the forward statement.
     * @param abstractNode The node which is being visited.
     */
    void pre(int printLevel, AbstractNode abstractNode);

    /**
     * @param printLevel   the level, used connect decide how many indents there should be in the forward statement.
     * @param abstractNode The node which is being visited.
     */
    void post(int printLevel, AbstractNode abstractNode);
}
