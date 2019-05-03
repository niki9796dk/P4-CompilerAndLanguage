package SymbolTableImplementation;

import AST.Nodes.AbstractNodes.Node;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNode;
import AST.Nodes.NodeClasses.NamedNodes.AssignNode;
import AST.Nodes.NodeClasses.NamedNodes.GroupNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.BlockNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.OperationTypeNode;
import CodeGeneration.DataFlow.Operations.BinaryOperations.MatrixOperations.Multiplication;
import Enums.AnsiColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScopeTest {

    NamedNode n;
    Scope s;


    @BeforeEach
    void beforeEach() {
        n = new GroupNode();
        s = new Scope("id", n);
    }


    @Test
    void getId() {
        assertEquals("id", s.getId());
    }

    @Test
    void setVariable01() {
        s.setVariable(new BlockNode("blocknode_id"));
    }

    @Test
    void setVariable02() {
        Node node = new AssignNode();
        assertThrows(IllegalArgumentException.class, () -> s.setVariable(node));
    }

    @Test
    void getVariable01() {
        BlockNode node = new BlockNode("blocknode_id");
        s.setVariable(node);
        assertSame(s.getVariable("blocknode_id").getNode(), node);
    }

    @Test
    void getVariable02() {
        BlockNode node = new BlockNode("blocknode_id");
        s.setVariable(node);
        assertSame(s.getVariable(node).getNode(), node);
    }

    @Test
    void getVariable03() {
        Node node = new BlockNode("blocknode_id");
        s.setVariable(node);
        assertSame(s.getVariable(node).getNode(), node);
    }

    @Test
    void getVariable04() {
        Node node = new AssignNode();
        assertThrows(IllegalArgumentException.class, () -> s.getVariable(node));
    }

    @Test
    void getNode() {
        assertSame(s.getNode(), n);
    }

    @Test
    void toString000() {
        assertEquals(s.toString(), AnsiColor.BLUE + "\tSubScope id" + AnsiColor.RESET + ": \n" + AnsiColor.RED + "\t\t\tEmpty" + AnsiColor.RESET);
    }
}