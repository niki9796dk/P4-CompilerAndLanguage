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
    private CallParams paramStatement;

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
