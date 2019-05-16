package CodeGeneration.Building.Statements.Instantiations;

import CodeGeneration.Building.Statement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InitBuild implements Statement {
    private String buildId;
    private List<Statement> params = new ArrayList<>();

    public InitBuild(String buildId) {
        this.buildId = buildId;
    }

    public InitBuild(String buildId, Statement ... paramStatements) {
        this(buildId);
        this.params = Arrays.asList(paramStatements);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("(new ")
                .append(buildId)
                .append("(");



        builder.append("))");

        return builder.toString();
    }
}
