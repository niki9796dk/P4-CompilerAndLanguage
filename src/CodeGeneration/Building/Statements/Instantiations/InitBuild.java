package CodeGeneration.Building.Statements.Instantiations;

import CodeGeneration.Building.Statement;
import CodeGeneration.Building.Statements.Calls.CallParams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InitBuild implements Statement {
    private String buildId;
    private Statement params;

    public InitBuild(String buildId) {
        this.buildId = buildId;
    }

    public InitBuild(String buildId, Statement paramStatements) {
        this(buildId);
        this.params = paramStatements;
    }

    @Override
    public String toString() {
        String paramsString = (params != null) ? params.toString() : "()";

        return "new " + buildId + paramsString;
    }
}
