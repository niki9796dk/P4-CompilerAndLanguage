package CodeGeneration.Building;

import java.util.Collection;

public interface StatementCollection {
    void addStatement(Statement statement);

    Collection<Statement> getStatementList();
}
