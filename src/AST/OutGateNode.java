package AST;

public class OutGateNode extends AbstractNode implements IdNode {
    private String name;
    private String id;


    public OutGateNode(String id) {
        this.id = id;
        this.name = "InGate";
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
