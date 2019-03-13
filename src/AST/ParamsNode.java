package AST;

public class ParamsNode extends AbstractNode{
    private String name;

    public ParamsNode() {
        this.name = "Params";
    }

    public String getName() { return name; }

    public String toString() {
        return(getName());
    }
}
