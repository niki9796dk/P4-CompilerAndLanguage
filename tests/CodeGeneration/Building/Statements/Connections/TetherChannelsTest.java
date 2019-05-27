package CodeGeneration.Building.Statements.Connections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class TetherChannelsTest {

    // Fields:
    private TetherChannels tetherChannels;

    // Constants:
    private static final String DEFAULT_STRING = "string";

    @BeforeEach
    void beforeEach() {
        this.tetherChannels = new TetherChannels(DEFAULT_STRING, DEFAULT_STRING);
    }

    @Test
    void toString01() {
        assertNotNull(this.tetherChannels.toString());
    }

}