package CodeGeneration.Building;

import java.util.Collection;
import java.util.List;

public interface StatementCollection {
    void addStatement(Statement statement);
    Collection<Statement> getStatementList();
}
