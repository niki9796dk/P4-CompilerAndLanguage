package AST;

public abstract class IdentifierNode extends AbstractNode {
    protected Identifier id;

    public IdentifierNode(String id, boolean isThis) {
        this.id = new Identifier(id, isThis);
    }

    public String getId() { return id.getId(); }

    public boolean isThis() { return this.id.isThis(); }

    public AbstractNode setId(String id) {
        this.id.setId(id);

        return this;
    }

    public AbstractNode setIsThis(boolean isThis) {
        this.id.setThis(isThis);

        return this;
    }
}
