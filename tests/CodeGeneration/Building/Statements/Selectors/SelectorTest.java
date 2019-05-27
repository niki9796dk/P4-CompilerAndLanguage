package CodeGeneration.Building.Statements.Selectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SelectorTest {

    // Fields:
    private Selector selector;

    // Constant:
    private static final String DEFAULT_ID = "id";

    @BeforeEach
    void beforeEach() {
        this.selector = new Selector(DEFAULT_ID);
    }

    @Test
    void toString1() {
        assertEquals(DEFAULT_ID, this.selector.toString());
    }
}