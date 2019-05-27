package CodeGeneration.Building.Statements.Selectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DotSelectorTest {

    // Field:
    private DotSelector dotSelector;

    // Constant:
    private static final String DEFAULT_ID = "id";
    private static final String RESULT = DEFAULT_ID + ".getChannel(\"" + DEFAULT_ID + "\")";

    @BeforeEach
    void beforeEach() {
        this.dotSelector = new DotSelector(DEFAULT_ID, DEFAULT_ID);
    }

    @Test
    void toString1() {
        assertEquals(RESULT, this.dotSelector.toString());
    }
}