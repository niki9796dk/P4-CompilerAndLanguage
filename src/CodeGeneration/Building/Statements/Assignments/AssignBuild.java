package CodeGeneration.Building.Statements.Assignments;

import CodeGeneration.Building.Statement;
import CodeGeneration.Building.Statements.Instantiations.InitBuild;
import CodeGeneration.Building.Statements.SubStatement;

public class AssignBuild implements Statement {
    private String leftVar;
    private InitBuild initBuild;

    private AssignBuild(String leftVar) {
        this.leftVar = leftVar;
    }

    public AssignBuild(String leftVar, InitBuild initBuild) {
        this(leftVar);
        this.initBuild = initBuild;
    }

    public AssignBuild(String leftVar, String initBuild) {
        this(leftVar, new InitBuild(initBuild));
    }

    public AssignBuild(String leftVar, String initBuild, Statement ... paramStatements) {
        this(leftVar, new InitBuild(initBuild, paramStatements));
    }

    @Override
    public String toString() {
        return leftVar + " = " + initBuild;
    }
}
