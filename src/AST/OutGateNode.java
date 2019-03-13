package AST;

public class OutGateNode extends AbstractNode implements TypeNode {
    private String id;
    private String name;


    public OutGateNode(String id) {
        this.id = id;
        this.name = "OutGate";
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
