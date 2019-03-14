package AST;

public class BlueprintTypeNode extends AbstractNode implements IdNode{
    private String name;
    private String id;

    public BlueprintTypeNode(String id) {
        this.id = id;
        this.name = "BlueprintType";
    }

    public String getName() { return name; }

    public String toString() {
        return(getName() + " " + this.id);
    }

    public String getId() {
        return id;
    }

    @Override
    public AbstractNode setId(String id) {
        this.id = id;

        return this;
    }
}
