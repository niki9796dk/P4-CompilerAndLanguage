package AST;

public class BlockNode extends AbstractNode {

    private String name;
    private String id;


    public BlockNode(String id) {
        this.id = id;
        this.name = "Block";
    }

    public String getName() { return name; }
    public String getId() { return id; }

}