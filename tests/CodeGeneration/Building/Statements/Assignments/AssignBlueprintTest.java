package CodeGeneration.Building.Statements.Assignments;

import CodeGeneration.Building.Statements.Instantiations.InitBlueprint;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AssignBlueprintTest {

    // Fields:
    private AssignBlueprint assignBlueprint01;

    private InitBlueprint initBlueprint;

    // constants:
    private static final String LEFT_VAR = "left var";
    private static final String INIT_BUILD = "Init build";

    @Test
    void constructors() {
        this.initBlueprint = new InitBlueprint(INIT_BUILD);
        this.assignBlueprint01 = new AssignBlueprint(LEFT_VAR, this.initBlueprint);
    }

    @Test
    void toString1() {
        this.assignBlueprint01 = new AssignBlueprint(LEFT_VAR, this.initBlueprint);
        assertNotNull(this.assignBlueprint01.toString());
    }
}