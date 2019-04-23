package AST;

import AST.Nodes.AbstractNodes.Nodes.AbstractNode;

/**
 *
 */
public interface Visitor {
    /**
     * @param printLevel
     * @param a
     */
    public void pre(int printLevel, AbstractNode a);

    /**
     * @param printLevel
     * @param a
     */
    public void post(int printLevel, AbstractNode a);
}
