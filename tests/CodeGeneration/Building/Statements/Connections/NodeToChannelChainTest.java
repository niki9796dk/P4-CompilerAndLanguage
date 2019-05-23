package CodeGeneration.Building.Statements.Connections;

import CodeGeneration.Building.Statements.Selectors.Selector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NodeToChannelChainTest {

    // Fields:
    private NodeToChannelChain nodeToChannelChain;

    // Constants:
    private static final String DEFAULT_ID = "id";

    @BeforeEach
    void beforeEach() {
        this.nodeToChannelChain = new NodeToChannelChain(DEFAULT_ID, DEFAULT_ID);
        NodeToChannelChain nodeToChannelChain02 = new NodeToChannelChain(new Selector(DEFAULT_ID), new Selector(DEFAULT_ID));
    }

    @Test
    void toString1() {
        assertNotNull(this.nodeToChannelChain.toString());
    }
}