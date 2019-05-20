package CodeGeneration.Building.StatementCollections;

import CodeGeneration.Building.Statement;
import CodeGeneration.Building.StatementCollection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ListStatements implements StatementCollection {

    private List<Statement> statements = new ArrayList<>();

    @Override
    public void addStatement(Statement statement) {
        this.statements.add(statement);
    }

    @Override
    public Collection<Statement> getStatementList() {
        return this.statements;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("{\n");

        for (Statement statement : this.getStatementList()) {
            builder.append(statement)
                    .append(";\n");
        }

        builder.append("}");

        return builder.toString();
    }
}
