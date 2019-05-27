package CodeGeneration.Building.Statements.Assignments;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class AssignVarTest {

    // Fields:
    private AssignVar assignVar;

    // Constants:
    private static final String DEFAULT_STRING = "string";

    @BeforeEach
    void beforeEach() {
        this.assignVar = new AssignVar(DEFAULT_STRING, DEFAULT_STRING);
    }

    @Test
    void toString1() {
        assertNotNull(this.assignVar.toString());
    }
}