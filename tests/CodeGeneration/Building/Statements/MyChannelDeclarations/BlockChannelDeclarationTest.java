package CodeGeneration.Building.Statements.MyChannelDeclarations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BlockChannelDeclarationTest {

    // Field:
    private BlockChannelDeclaration blockChannelDeclaration;

    // Constant:
    private static final String DEFAULT_ID = "id";
    private static final String RESULT = "this.addNewInputLabel" + "(\"" + DEFAULT_ID + "\", new ListChannel())";

    @BeforeEach
    void beforeEach() {
        this.blockChannelDeclaration = new BlockChannelDeclarationIn(DEFAULT_ID);
    }

    @Test
    void toString1() {
        assertEquals(RESULT, this.blockChannelDeclaration.toString());
    }
}