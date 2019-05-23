package CodeGeneration.Building.Statements.Assignments;

import CodeGeneration.Building.Statement;
import CodeGeneration.Building.Statements.Instantiations.InitBlueprint;
import CodeGeneration.Building.Statements.Instantiations.InitSize;

public class AssignSize implements Statement {

    // Fields:
    private String leftVar;
    private InitSize initSize;

    // Constructors:
    private AssignSize(String leftVar) {
        this.leftVar = leftVar;
    }

    public AssignSize(String leftVar, InitSize initSize) {
        this(leftVar);
        this.initSize = initSize;
    }

    @Override
    public String toString() {
        return leftVar + " = " + initSize;
    }
}
