package CodeGeneration.Building;

import CodeGeneration.Building.CodeScopes.SimpleCodeScope;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BlockClassTest {

    // Fields:
    private BlockClass blockClass;

    // Constants:
    private static final String DEFAULT_STRING = "string";

    @BeforeEach
    void beforeEach() {
        this.blockClass = new BlockClass(DEFAULT_STRING, DEFAULT_STRING);
    }

    @Test
    void getClassName() {
        assertEquals(DEFAULT_STRING, this.blockClass.getClassName());
    }

    @Test
    void getBlueprint() {
        assertNotNull(this.blockClass.getBlueprint());
    }

    @Test
    void addProcedure() { // No error while adding a procedure
        this.blockClass.addProcedure(new SimpleCodeScope(DEFAULT_STRING));
        assertTrue(true);
    }

    @Test
    void addImport() {
        this.blockClass.addImport(DEFAULT_STRING);
    }

    @Test
    void toString01() {
        assertNotNull(this.blockClass.toString());
    }

    @Test
        // With procedure
    void toString02() {
        this.blockClass.addProcedure(new SimpleCodeScope(DEFAULT_STRING));
        assertNotNull(this.blockClass.toString());
    }
}