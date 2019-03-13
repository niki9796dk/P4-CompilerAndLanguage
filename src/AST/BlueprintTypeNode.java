package AST;

public class BlueprintTypeNode extends AbstractNode implements TypeNode {
    private String name;
    private String id;

    public BlueprintTypeNode(String id) {
        this.id = id;
        this.name = "BlueprintType";
    }

    public String getName() { return name; }
    public String getId() { return id; }

    public String toString() {
        return(getName() + " " + this.id);
    }

    @Override
    public AbstractNode setId(String id) {
        this.id = id;

        return this;
    }
}
