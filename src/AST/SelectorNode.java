package AST;

public class SelectorNode extends IdentifierNode {
    private String name;

    public SelectorNode(String id) {
        super(id);
        name = "Selector";
    }

    public String getName() { return name; }

    public String toString() {
        return(getName() + " " + this.id);
    }
}
