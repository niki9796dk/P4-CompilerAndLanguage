package CodeGeneration.Building.Statements.Instantiations;

import CodeGeneration.Building.Statement;
import CodeGeneration.Building.Statements.Calls.CallParams;

public class InitBuildBlueprint implements Statement {

    // Fields:
    private String buildId;
    private CallParams paramStatement;

    // Constructors:
    public InitBuildBlueprint(String buildId) {
        this.buildId = buildId;
    }

    public InitBuildBlueprint(String buildId, CallParams paramStatement) {
        this(buildId);
        this.paramStatement = paramStatement;
    }

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();

        builder
                .append("(")
                .append("(Block) ")
                .append(buildId)
                .append(".getConstructor")
                .append(this.paramStatement != null ? this.paramStatement.asConstructorPar() : "()")
                .append(".newInstance")
                .append(this.paramStatement != null ? this.paramStatement.toString() : "()")
                .append(")");

        return builder.toString();
    }
}
