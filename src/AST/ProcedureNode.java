package AST;

public class ProcedureNode extends AbstractNode {

    private String name;
    private String id;


    public ProcedureNode(String id) {
        this.id = id;
        this.name = "Procedure";
    }

    public String getName() { return name; }
    public String getId() { return id; }
    public String toString() {
        return(getName() + " " + this.id);
    }


}