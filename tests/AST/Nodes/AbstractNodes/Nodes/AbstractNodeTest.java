package AST.Nodes.AbstractNodes.Nodes;

import AST.Nodes.NodeClasses.NamedNodes.ChainNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.BuildNode;
import java_cup.runtime.ComplexSymbolFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AbstractNodeTest {

    // Field:
    private AbstractNode node1;
    private AbstractNode node2;
    private AbstractNode node3;
    private AbstractNode node4_chain;

    @BeforeEach
    void beforeEach() {
        this.node1 = new BuildNode("build_01", new ComplexSymbolFactory.Location(-1, -1));
        this.node2 = new BuildNode("build_02", new ComplexSymbolFactory.Location(-1, -1));
        this.node3 = new BuildNode("build_03", new ComplexSymbolFactory.Location(-1, -1));
        this.node4_chain = new ChainNode(new ComplexSymbolFactory.Location(-1, -1));
    }

    @Test
    void makeSibling01() {
        this.node1.makeSibling(this.node2);
        assertSame(this.node2, this.node1.getSib());
    }

    @Test
    void makeSibling02() {
        this.node2.makeSibling(this.node3);
        this.node1.makeSibling(this.node2);
        assertSame(this.node3, this.node1.getSib().getSib());
    }

    @Test
    void adoptChildren01() {
        this.node1.adoptChildren(this.node2);
        assertSame(this.node2, this.node1.getChild());
    }

    @Test
    void adoptChildren02() {
        this.node1.adoptChildren(this.node2, this.node3);
        assertSame(this.node3, this.node1.getChild().getSib());
    }

    @Test
    void adoptChildren03() {
        this.node2.adoptChildren(this.node3);
        this.node1.adoptChildren(this.node2);
        assertSame(this.node3, this.node1.getChild().getChild());
    }

    @Test
    void adoptAsFirstChild() {
        this.node1.adoptChildren(this.node2);
        this.node1.adoptAsFirstChild(this.node3);
        assertSame(this.node3, this.node1.getChild());
    }

    @Test
    void orphan() {
        this.node1.makeSibling(this.node2);
        this.node1.orphan();
        assertFalse(this.node2.equals(this.node1.getSib()));
    }

    @Test
    void abandonChildren() {
        this.node1.adoptChildren(this.node2);
        this.node1.abandonChildren();
        assertFalse(this.node2.equals(this.node1.getChild()));
    }

    @Test
    void findFirstChildOfClass() {
        this.node1.adoptChildren(this.node4_chain, this.node2);
        assertSame(this.node2, this.node1.findFirstChildOfClass(BuildNode.class));
    }

    @Test
    void countChildren() {
        this.node1.adoptChildren(this.node2, this.node3);
        assertEquals(2, this.node1.countChildren());
    }

    @Test
    void countChildrenInstanceOf() {
        this.node1.adoptChildren(this.node2, this.node3, this.node4_chain);
        assertEquals(2, this.node1.countChildrenInstanceOf(BuildNode.class));
    }

    @Test
    void getParent() {
        this.node1.adoptChildren(this.node2);
        assertEquals(this.node1, this.node2.getParent());
    }

    @Test
    void getSib() {
        this.node1.adoptChildren(this.node2, this.node3);
        assertEquals(this.node3, this.node2.getSib());
    }

    @Test
    void getChild() {
        this.node1.adoptChildren(this.node2);
        assertEquals(this.node2, this.node1.getChild());
    }

    @Test
    void getFirstSib() {
        this.node1.adoptChildren(this.node2, this.node3);
        assertEquals(this.node2, this.node3.getFirstSib());
    }
}