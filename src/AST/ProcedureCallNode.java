package AST;

public class ProcedureCallNode extends AbstractNode {
    private String name;
    private String id;

    public ProcedureCallNode() {
        this.name = "ProcedureCall";
    }

    public String getName() { return name; }

    public String toString() {
        return(getName() + " " + id);
    }

}
