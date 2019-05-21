package CodeGeneration.Building.Statements.Assignments;

import CodeGeneration.Building.Statement;

public class AssignVar implements Statement {
    private String leftVar;
    private String rightVar;

    public AssignVar(String leftVar, String rightVar) {
        this.leftVar = leftVar;
        this.rightVar = rightVar;
    }

    @Override
    public String toString() {
        return leftVar + " = " + rightVar;
    }
}
