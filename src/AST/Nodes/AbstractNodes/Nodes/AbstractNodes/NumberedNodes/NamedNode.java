package AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNode;

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
