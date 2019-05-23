package AST.Nodes.NodeClasses.NamedNodes;

import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNode;
import java_cup.runtime.ComplexSymbolFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SizeNodeTest {

    @Test
    void equals01() {
        SizeNode sn = new SizeNode(1, 2, new ComplexSymbolFactory.Location(-1, -1));

        assertEquals(sn, sn);
    }

    @Test
    void equals02() {
        SizeNode sn1 = new SizeNode(1, 2, new ComplexSymbolFactory.Location(-1, -1));
        SizeNode sn2 = new SizeNode(2, 1, new ComplexSymbolFactory.Location(-1, -1));

        assertNotEquals(sn1, sn2);
        assertNotEquals(sn2, sn1);
    }

    @Test
    void equals03() {
        SizeNode sn = new SizeNode(1, 2, new ComplexSymbolFactory.Location(-1, -1));

        assertNotEquals(null, sn);
    }

    @Test
    void equals04() {
        SizeNode sn1 = new SizeNode(1, 2, new ComplexSymbolFactory.Location(-1, -1));
        SizeNode sn2 = new SizeNode(1, 2, new ComplexSymbolFactory.Location(-1, -1));

        assertEquals(sn1.hashCode(), sn2.hashCode());
        assertTrue(sn1.equals(sn2) && sn2.equals(sn1));
    }

    @Test
    void toStringTest() {
        SizeNode sn = new SizeNode(1, 2, new ComplexSymbolFactory.Location(-1, -1));

        assertEquals("[-1] Size [1, 2]", sn.toString());
    }
}