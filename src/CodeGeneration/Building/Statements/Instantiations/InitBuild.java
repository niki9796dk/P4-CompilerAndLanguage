package CodeGeneration.Building.Statements.Instantiations;

import CodeGeneration.Building.Statement;
import CodeGeneration.Building.Statements.SubStatement;

public class InitBuild implements SubStatement {
    private String buildId;

    public InitBuild(String buildId) {
        this.buildId = buildId;
    }

    @Override
    public String toString() {
        return "(new " + buildId + "())";
    }
}
