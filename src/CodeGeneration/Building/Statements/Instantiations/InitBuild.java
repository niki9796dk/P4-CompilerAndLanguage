package CodeGeneration.Building.Statements.Instantiations;

import CodeGeneration.Building.Statement;

public class InitBuild implements Statement {

    // Fields:
    private String buildId;
    private Statement params;

    // Constructors:
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
