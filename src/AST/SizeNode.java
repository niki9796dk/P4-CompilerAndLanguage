package AST;

public class SizeNode extends AbstractNode implements IdNode {
    private String name;
    private String id;
    private String value; // TODO: Change this to two different ints

    public SizeNode(String id) {
        this.id = id;
        this.name = "SizeType";
    }

    public String getName() { return name; }

    public String getId() {
        return id;
    }

    public String toString() {
        return(getName() + " " + this.id);
    }

    @Override
    public AbstractNode setId(String id) {
        this.id = id;

        return this;
    }
}
