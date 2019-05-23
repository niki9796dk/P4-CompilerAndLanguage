package CodeGeneration.Building.Statements.Instantiations;

import CodeGeneration.Building.Statements.Selectors.Selector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InitBuildTest {

    // Fields:
    private InitBuild initBuild;

    // Constant:
    private static final String DEFAULT_ID = "id";

    @BeforeEach
    void beforeEach( ){
        this.initBuild = new InitBuild(DEFAULT_ID, new Selector(DEFAULT_ID));
    }

    @Test
    void toString01() {
        assertNotNull(this.initBuild.toString());
    }

    @Test
    void toString02() {
        this.initBuild = new InitBuild(DEFAULT_ID, null);
        assertNotNull(this.initBuild.toString());
    }
}