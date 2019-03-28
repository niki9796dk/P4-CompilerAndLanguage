package AST;

import AST.Nodes.AbstractNodes.Nodes.AbstractNode;

public interface Visitor {
    public void pre(int i, AbstractNode a);
    public void post(int i, AbstractNode a);
}
