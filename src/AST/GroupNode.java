package AST;

public class GroupNode extends AbstractNode {
    private String name;

    public GroupNode() {
        this.name = "Group";
    }

    public String getName() { return name; }

}