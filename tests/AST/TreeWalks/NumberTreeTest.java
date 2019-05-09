package AST.TreeWalks;

import AST.Nodes.AbstractNodes.Nodes.AbstractNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNode;
import AST.Nodes.NodeClasses.NamedNodes.AssignNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NumberTreeTest {
    private NumberTree numberTree;
    private NumberedNode numberedNode;

    @BeforeEach
    void makeNumberTree() {
        numberTree = new NumberTree();
        numberedNode = new AssignNode();
    }

    @Test
    void preTest01() {
        numberTree.pre(0, numberedNode);
        assertEquals(0, numberedNode.getNumber());
    }

    @Test
    void postTest01() {
        numberTree.post(0, numberedNode);
        assertEquals(-1, numberedNode.getNumber());
    }
}