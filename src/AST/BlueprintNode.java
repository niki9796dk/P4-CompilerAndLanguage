package AST;

public class BlueprintNode extends AbstractNode {
    private String name;

    public BlueprintNode() {
        this.name = "Blueprint";
    }

    public String getName() { return name; }

    public String toString() {
        return(getName());
    }

}
