package CodeGeneration.Building.Statements.Assignments;

import CodeGeneration.Building.Statements.Instantiations.InitSize;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AssignSizeTest {

    // Fields:
    private AssignSize assignSize01;

    // Constants:
    private static final String DEFAULT_ID = "id";
    private static final int VALUE_A = 1;
    private static final int VALUE_B = 2;

    @BeforeEach
    void beforeEach() {
        this.assignSize01 = new AssignSize(DEFAULT_ID, new InitSize(VALUE_A, VALUE_B));
    }

    @Test
    void toString01() {
        assertNotNull(this.assignSize01.toString());
    }
}