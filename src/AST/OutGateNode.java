package AST;

public class OutGateNode extends AbstractNode {
    private String id;
    private String name;


    public OutGateNode(String id) {
        this.id = id;
        this.name = "OutGate";
    }

    public String getName() { return name; }
    public String getId() { return id; }

}
