package AST;

public class InChannelNode extends AbstractNode implements IdNode{
    private String name;
    private String id;

    public InChannelNode(String id) {
        this.id = id;
        this.name = "InChannel";
    }

    public String getName() { return name; }

    public String toString() {
        return(getName() + " " + this.id);
    }

    public String getId() {
        return id;
    }

    @Override
    public AbstractNode setId(String id) {
        this.id = id;

        return this;
    }
}
