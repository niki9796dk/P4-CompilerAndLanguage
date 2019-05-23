package AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes;

import java_cup.runtime.ComplexSymbolFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BlockNodeTest {

    // Fields:
    private BlockNode node1;
    private BlockNode node2;

    @BeforeEach
    void beforeEach() {
        this.node1 = new BlockNode("block_test", new ComplexSymbolFactory.Location(-1, -1));
        this.node2 = new BlockNode("block_test", new ComplexSymbolFactory.Location(-1, -1));
    }
    @Test
    void toString01() {
        assertTrue(this.node1.toString().endsWith("block_test"));
    }

    @Test
    void equals01() {
        assertEquals(this.node1, this.node2);
    }

    @Test
    void hashCode01() {
        assertEquals(this.node1.hashCode(), this.node2.hashCode());
    }
}