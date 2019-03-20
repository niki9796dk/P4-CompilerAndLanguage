package AST;

public class AssignNode extends AbstractNode {
    private String name;

    public AssignNode() {
        this.name = "Assign";
    }

    public String getName() { return name; }

}