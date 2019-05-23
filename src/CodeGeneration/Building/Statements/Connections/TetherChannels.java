package CodeGeneration.Building.Statements.Connections;

import CodeGeneration.Building.Statement;

public class TetherChannels implements Statement {

    // Fields:
    private String left;
    private String right;

    // Constructors:
    public TetherChannels(String left, String right) {
        this.left = left;
        this.right = right;
    }

    public TetherChannels(Statement left, Statement right) {
        this(left.toString(), right.toString());
    }

    @Override
    public String toString() {
        return left + ".tether("+ right +")";
    }
}
