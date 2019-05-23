package CodeGeneration.Building.StatementCollections;

import CodeGeneration.Building.Statement;
import CodeGeneration.Building.Statements.Selectors.Selector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ListStatementsTest {

    // Fields:
    private ListStatements listStatements;
    private Statement statement;

    // Constant:
    private static final String DEFAULT_ID = "id";

    @BeforeEach
    void beforeEach() {
        this.listStatements = new ListStatements();
        this.statement = new Selector(DEFAULT_ID);
    }

    @Test
    void addStatement() {
        this.listStatements.addStatement(this.statement);
        assertTrue(this.listStatements.getStatementList().contains(this.statement));
    }

    @Test
    void getStatementList() {
        assertTrue(this.listStatements.getStatementList().isEmpty());
    }

    @Test
    void toString01() {
        assertNotNull(this.listStatements.toString());
    }

    @Test
    void toString02() {
        this.listStatements.addStatement(this.statement);
        assertNotNull(this.listStatements.toString());
    }
}