package CodeGeneration.Building.CodeScopes;

import CodeGeneration.Building.*;
import CodeGeneration.Building.ParameterCollections.ListParameters;
import CodeGeneration.Building.StatementCollections.ListStatements;

import java.util.Collection;

public class SimpleCodeScope implements CodeScope {
    protected String id;
    protected StatementCollection statements = new ListStatements();
    protected ParameterCollection parameters = new ListParameters();

    public SimpleCodeScope(String id) {
        this.setId(id);
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public Collection<Statement> getStatementList() {
        return this.statements.getStatementList();
    }

    @Override
    public Collection<Parameter> getParameterList() {
        return this.parameters.getParameterList();
    }

    @Override
    public String toCallParameters() {
        return this.parameters.toCallParameters();
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void addStatement(Statement statement) {
        this.statements.addStatement(statement);
    }

    @Override
    public void addParameter(Parameter parameter) {
        this.parameters.addParameter(parameter);
    }

    @Override
    public ParameterCollection getParameters() {
        return this.parameters;
    }

    @Override
    public StatementCollection getStatements() {
        return this.statements;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("private void ")         // Append start of function declaration.
                .append(this.getId())           // Append the id of the blueprint/procedure.
                .append(" ")                    // Spacer between function name and parameters.
                .append(this.getParameters())   // Append params.
                .append(" ")                    // Spacer between params and statements.
                .append(this.getStatements())   // append statements.
                .append("\n");                  // Append new line for ease of reading

        return builder.toString();
    }
}