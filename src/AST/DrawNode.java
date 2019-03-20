package AST;

public class DrawNode extends AbstractNode {
    private String name;
    private String id;

    public DrawNode(String id) {
        this.id = id;
        this.name = "Draw";
    }

    public String getName() { return name; }
    public String getId() { return id; }
    public String toString() {
        return(getName() + " " + this.id);
    }

}
