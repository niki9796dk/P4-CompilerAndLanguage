package AST.Nodes.AbstractNodes;

import AST.Enums.NodeEnum;

public abstract class NamedNode extends NumberedNode {
    private String name;

    public NamedNode(String name, NodeEnum nodeEnum) {
        super(nodeEnum);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return super.toString() + " " + this.name;
    }
}
