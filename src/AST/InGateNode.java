package AST;

public class InGateNode extends AbstractNode{
    private String name;
    private String id;


    public InGateNode(String id) {
        this.id = id;
        this.name = "InGate";
    }

    public String getName() { return name; }
    public String getId() { return id; }
}
