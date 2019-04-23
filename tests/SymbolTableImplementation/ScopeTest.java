package SymbolTableImplementation;

import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNode;
import AST.Nodes.NodeClasses.NamedNodes.GroupNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.BlockNode;
import Enums.AnsiColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

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
    void setVariable() {
        s.setVariable(new BlockNode("blocknode_id"));
    }

    @Test
    void getVariable() {
        BlockNode node = new BlockNode("blocknode_id");
        s.setVariable(node);
        assertSame(s.getVariable("blocknode_id").getNode(), node);
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