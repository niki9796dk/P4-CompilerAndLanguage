package AST;

public class TemporaryNode extends AbstractNode {

    private String s;

    public TemporaryNode(String s) {
        this.s = s;
    }

    public String getName() { return s; }

}