package AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNode;

public abstract class NamedIdNode extends NamedNode {
    private String id;

    public NamedIdNode(String name, String id, NodeEnum nodeEnum) {
        super(name, nodeEnum);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public AbstractNode setId(String id) {
        this.id = id;
        return this;
    }

    @Override
    public String toString() {
        return super.toString() + " " + this.id;
    }
}
