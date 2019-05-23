package CodeGeneration.Building.Statements.Instantiations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InitSizeTest {

    // Fields:
    private InitSize initSize;

    // Constants:
    private static final int VALUE_A = 1, VALUE_B = 2;
    private static final String INIT_STRING = "new Pair<Integer, Integer>(" + VALUE_A + ", " + VALUE_B + ")";

    @BeforeEach
    void beforeEach() {
        this.initSize = new InitSize(VALUE_A, VALUE_B);
    }

    @Test
    void toString01() {
        assertEquals(INIT_STRING, this.initSize.toString());
    }
}