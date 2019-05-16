package CodeGeneration.Building.Statements.Assignments;

import CodeGeneration.Building.Statement;
import CodeGeneration.Building.Statements.Instantiations.InitBuild;
import CodeGeneration.Building.Statements.SubStatement;

public class AssignBuild implements Statement {
    private String leftVar;
    private SubStatement initBuild;

    public AssignBuild(String leftVar, String initBuild) {
        this.leftVar = leftVar;
        this.initBuild = new InitBuild(initBuild);
    }

    @Override
    public String toString() {
        return leftVar + " = " + initBuild;
    }
}
