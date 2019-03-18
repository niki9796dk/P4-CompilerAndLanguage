package AST;

public abstract class IdentifierNode extends AbstractNode {
    protected Identifier id;

    public IdentifierNode(String id) {
        this.id = new Identifier(id);
    }

    public String getId() { return id.getId(); }

    public AbstractNode setId(String id) {
        this.id.setId(id);

        return this;
    }
}
