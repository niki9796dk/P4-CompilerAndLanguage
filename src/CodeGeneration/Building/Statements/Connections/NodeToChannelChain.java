package CodeGeneration.Building.Statements.Connections;

import CodeGeneration.Building.Statement;

public class NodeToChannelChain implements Statement {

    // Fields:
    private String left;
    private String right;

    // Constructors:
    public NodeToChannelChain(String left, String right) {
        this.left = left;
        this.right = right;
    }

    public NodeToChannelChain(Statement left, Statement right) {
        this(left.toString(), right.toString());
    }

    @Override
    public String toString() {
        return left + ".connectTo(" + right + ")";
    }
}
