package AST;

public class StorageNode extends AbstractNode {
    private AbstractNode[] nodes;

    public StorageNode(AbstractNode ...nodes) {
        this.nodes = nodes;
    }

    public AbstractNode[] getNodes() {
        return nodes;
    }
}
