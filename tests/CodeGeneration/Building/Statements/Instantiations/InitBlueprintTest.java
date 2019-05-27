package CodeGeneration.Building.Statements.Instantiations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InitBlueprintTest {

    // Fields:
    private InitBlueprint initBlueprint;

    // Constant:
    private static final String DEFAULT_ID = "id", POSTFIX = ".class";

    @BeforeEach
    void beforeEach() {
        this.initBlueprint = new InitBlueprint(DEFAULT_ID);
    }

    @Test
    void toString1() {
        assertEquals(DEFAULT_ID + POSTFIX, this.initBlueprint.toString());
    }
}