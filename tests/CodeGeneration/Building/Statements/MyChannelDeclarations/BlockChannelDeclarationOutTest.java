package CodeGeneration.Building.Statements.MyChannelDeclarations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BlockChannelDeclarationOutTest {

    // Field:
    private BlockChannelDeclarationOut blockChannelDeclarationOut;

    // Constant:
    private static final String DEFAULT_ID = "id";
    private static final String RESULT = "this.addNewOutputLabel" + "(\"" + DEFAULT_ID + "\", new ListChannel())";

    @BeforeEach
    void beforeEach() {
        this.blockChannelDeclarationOut = new BlockChannelDeclarationOut(DEFAULT_ID);
    }

    @Test
    void toString1() {
        assertEquals(RESULT, this.blockChannelDeclarationOut.toString());
    }
}