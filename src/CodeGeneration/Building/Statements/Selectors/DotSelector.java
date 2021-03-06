package CodeGeneration.Building.Statements.Selectors;

import CodeGeneration.Building.Statement;

public class DotSelector implements Statement {

    // Fields:
    private String idFirst;
    private String idSecond;

    // Constructor:
    public DotSelector(String idFirst, String idSecond) {
        this.idFirst = idFirst;
        this.idSecond = idSecond;
    }

    @Override
    public String toString() {
        return this.idFirst + ".getChannel(\"" + this.idSecond + "\")";
    }
}
