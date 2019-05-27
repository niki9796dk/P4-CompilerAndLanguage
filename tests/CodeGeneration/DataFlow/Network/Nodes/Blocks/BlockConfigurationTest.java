package CodeGeneration.DataFlow.Network.Nodes.Blocks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class BlockConfigurationTest {

    // Field:
    private BlockConfiguration blockConfiguration;

    // Constants:
    private static final int ROWS = 3;
    private static final int ROWS_V2 = ROWS + 1;
    private static final double LEARNING_RATE = 0.2;
    private static final double LEARNING_RATE_V2 = LEARNING_RATE + 0.1;

    @BeforeEach
    void beforeEach() {
        this.blockConfiguration = new BlockConfiguration(ROWS, LEARNING_RATE);
    }

    @Test
    void getDataRows() {
        assertEquals(ROWS, this.blockConfiguration.getDataRows());
    }

    @Test
    void setDataRows01() {
        this.blockConfiguration.setDataRows(ROWS_V2);
        assertNotEquals(ROWS, this.blockConfiguration.getDataRows());
    }

    @Test
    void setDataRows02() {
        this.blockConfiguration.setDataRows(ROWS_V2);
        assertEquals(ROWS_V2, this.blockConfiguration.getDataRows());
    }

    @Test
    void getLearningRate() {
        assertEquals(LEARNING_RATE, this.blockConfiguration.getLearningRate());
    }

    @Test
    void setLearningRate01() {
        this.blockConfiguration.setLearningRate(LEARNING_RATE_V2);
        assertNotEquals(LEARNING_RATE, this.blockConfiguration.getLearningRate());
    }

    @Test
    void setLearningRate02() {
        this.blockConfiguration.setLearningRate(LEARNING_RATE_V2);
        assertEquals(LEARNING_RATE_V2, this.blockConfiguration.getLearningRate());
    }
}