package AST;

public class InGateNode extends AbstractNode implements TypeNode{
    private String name;
    private String id;


    public InGateNode(String id) {
        this.id = id;
        this.name = "InGate";
    }

    public String getName() { return name; }
    public String getId() { return id; }

    public String toString() {
        return(getName() + " " + this.id);
    }

    @Override
    public AbstractNode setId(String id) {
        this.id = id;

        return this;
    }
}
