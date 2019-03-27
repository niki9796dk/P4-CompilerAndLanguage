package AST.Nodes.AbstractNodes;

import AST.Enums.NodeEnum;

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
