package AST;

public class ProcedureCallNode extends AbstractNode implements IdNode{
    private String name;
    private String id;

    public ProcedureCallNode(String id) {
        this.name = "ProcedureCall";
        this.id = id;
    }

    public String getName() { return name; }

    public String toString() {
        return(getName() + " " + id);
    }

    @Override
    public AbstractNode setId(String id) {
        this.id = id;

        return this;
    }
}
