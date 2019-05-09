package AST.Nodes.AbstractNodes.Nodes.AbstractNodes;

import AST.Enums.NodeEnum;
import AST.Nodes.NodeClasses.NamedNodes.AssignNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NumberedNodeTest {

    private NumberedNode node1;

    @BeforeEach
    void beforeEach() {
        this.node1 = new AssignNode();
    }

    @Test
    void getNumber() {
        assertEquals(-1, this.node1.getNumber());
    }

    @Test
    void getNodeEnum() {
        assertEquals(NodeEnum.ASSIGN, this.node1.getNodeEnum());
    }

    @Test
    void setNumber() {
        this.node1.setNumber(1);
        assertEquals(1, this.node1.getNumber());
    }

    @Test
    void setNumber_exception() {
        assertThrows(IllegalArgumentException.class, () -> this.node1.setNumber(-1));
    }

    @Test
    void toString01() {
        this.node1.setNumber(1);
        assertEquals("[1] Assign", this.node1.toString());
    }
}