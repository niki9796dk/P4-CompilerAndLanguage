package AST.Nodes.NodeClasses.NamedNodes;

import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SizeNodeTest {

    // Fields:
    private SizeNode node1;
    private SizeNode node2;

    @BeforeEach
    void beforeEach() {
        this.node1 = new SizeNode(1, 2);
        this.node2 = new SizeNode(1, 2);
    }

    @Test
    void equals() {
        assertEquals(this.node1, this.node2);
    }

    @Test
    void hashCode01() {
        assertEquals(this.node1.hashCode(), this.node2.hashCode());
    }

    @Test
    void toString01() {
        assertEquals("[-1] Size [1, 2]", this.node1.toString());
    }
}