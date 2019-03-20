package AST;

public class StorageNode extends AbstractNode {
    private AbstractNode[] nodes;
    private String name;

    public StorageNode(AbstractNode ... nodes) {
        this("NoName", nodes);
    }

    public StorageNode(String name, AbstractNode ... nodes) {
        this.name = name;
        this.nodes = nodes;
    }

    public AbstractNode[] getNodes() {
        return nodes;
    }
    public String getName() { return this.name; }
}
