package AST;

public class BuildNode extends AbstractNode {

    private String name;
    private String id;


    public BuildNode(String id) {
        this.id = id;
        this.name = "Block";
    }

    public String getName() { return name; }
    public String getId() { return id; }
    public String toString() {
        return(getName() + " " + this.id);
    }


}