package CodeGeneration.Building.Statements.Instantiations;

import AST.Enums.NodeEnum;
import CodeGeneration.Building.Statement;
import CodeGeneration.Building.Statements.Calls.CallParams;
import CodeGeneration.Building.Statements.VariableDeclarations.Enums.JavaTypes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InitBuildBlueprint implements Statement {
    private String buildId;
    private List<Statement> params = new ArrayList<>();

    public InitBuildBlueprint(String buildId) {
        this.buildId = buildId;
    }

    public InitBuildBlueprint(String buildId, Statement ... paramStatements) {
        this(buildId);
        this.params = Arrays.asList(paramStatements);
    }

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();

        builder
                .append("(")
                .append("(Block) ")
                .append(buildId)
                .append(".getConstructor(");

        if (this.params.size() != 0) {
            for (Statement statement : this.params) {
                builder.append("(")
                        .append(statement)
                        .append(").class")
                        .append(", ");
            }

            builder.delete(builder.length()-2, builder.length()); // Remove last ", " sequence
        }

        builder
                .append(").newInstance")
                .append(new CallParams(this.params))
                .append(")");

        return builder.toString();
    }
}
