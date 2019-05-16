package CodeGeneration.Building.Statements.Calls;

import CodeGeneration.Building.Statement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CallParams implements Statement {
    private List<Statement> params;

    public CallParams(Statement ... paramStatements) {
        this.params = Arrays.asList(paramStatements);
    }

    public CallParams(List<Statement> params) {
        this.params = params;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("(");

        if (this.params.size() != 0) {
            for (Statement statement : this.params) {
                builder.append(statement)
                        .append(", ");
            }

            builder.subSequence(0, builder.length()-2); // Remove last ", " sequence
        }

        builder.append(")");

        return builder.toString();
    }
}
