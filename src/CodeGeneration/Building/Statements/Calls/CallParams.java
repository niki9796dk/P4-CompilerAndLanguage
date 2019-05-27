package CodeGeneration.Building.Statements.Calls;

import AST.Enums.NodeEnum;
import CodeGeneration.Building.Statement;
import CodeGeneration.Building.Statements.Selectors.Selector;
import SymbolTableImplementation.Scope;

import java.util.Arrays;
import java.util.List;

public class CallParams implements Statement {

    // Fields:
    private List<Statement> params;
    private Scope scope;

    // Constructors:
    public CallParams(Scope scope, Statement... paramStatements) {
        this.scope = scope;
        this.params = Arrays.asList(paramStatements);
    }

    public CallParams(Scope scope, List<Statement> params) {
        this.scope = scope;
        this.params = params;
    }

    public String asConstructorPar() {
        return this.asString(true);
    }

    @Override
    public String toString() {
        return this.asString(false);
    }

    private String asString(boolean asCall) {
        StringBuilder builder = new StringBuilder();

        builder.append("(");

        if (this.params.size() != 0) {
            for (Statement statement : this.params) {

                if (asCall) {
                    boolean isBlueprintVar = statement instanceof Selector && (scope.getVariable(statement.toString()).getSuperType() == NodeEnum.BLUEPRINT_TYPE);

                    builder.append("(")
                            .append("(Class) ")
                            .append("(")
                            .append(statement)
                            .append(")");

                    builder.append(".getClass()");

                    builder.append(")");

                } else {
                    builder.append(statement);
                }

                builder.append(", ");
            }

            builder.delete(builder.length() - 2, builder.length()); // Remove last ", " sequence
        }

        builder.append(")");

        return builder.toString();
    }
}
