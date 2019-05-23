package CodeGeneration.Building.Statements.Assignments;

import CodeGeneration.Building.Statement;
import CodeGeneration.Building.Statements.Instantiations.InitBlueprint;
import CodeGeneration.Building.Statements.Instantiations.InitBuild;

public class AssignBlueprint implements Statement {

    // Fields:
    private String leftVar;
    private InitBlueprint initBlueprint;

    // Constructors
    private AssignBlueprint(String leftVar) {
        this.leftVar = leftVar;
    }

    public AssignBlueprint(String leftVar, InitBlueprint initBlueprint) {
        this(leftVar);
        this.initBlueprint = initBlueprint;
    }

    public AssignBlueprint(String leftVar, String initBuild) {
        this(leftVar, new InitBlueprint(initBuild));
    }

    @Override
    public String toString() {
        return leftVar + " = " + initBlueprint;
    }
}
