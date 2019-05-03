package SymbolTableImplementation;

import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;
import AST.Nodes.NodeClasses.NamedNodes.AssignNode;
import AST.Nodes.NodeClasses.NamedNodes.BlueprintNode;
import AST.Nodes.NodeClasses.NamedNodes.ChannelDeclarationsNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.*;
import AST.Nodes.NodeClasses.NamedNodes.SizeNode;
import Enums.AnsiColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SymbolTableTest {

    SymbolTable s;
    BlockNode n;
    NamedIdNode n2;
    NamedIdNode n3;
    BlueprintNode bn;

    /**
     * Adds a block table connect the symbol table
     * Param: The node connect add connect the symbol table.
     */
    @BeforeEach
    void beforeEach() {
        s = new SymbolTable();
        n = new BlockNode("n");
        n2 = new SizeTypeNode("n2");
        n3 = new SizeTypeNode("n3");
        bn = new BlueprintNode();
    }

    @Test
    void openBlockScope() {
        s.openBlockScope(n);
    }

    @Test
    void openSubScope() {
        s.openBlockScope(n);
        s.openSubScope(bn);

        ChannelDeclarationsNode cdNode = new ChannelDeclarationsNode();
        ProcedureNode pNode = new ProcedureNode("pNode");
        SizeNode sNode = new SizeNode(0, 2);

        s.openSubScope(cdNode);
        s.openSubScope(pNode);
        assertThrows(IllegalArgumentException.class, () -> s.openSubScope(sNode));

    }

    @Test
    void getBlockScope() {
        s.openBlockScope(n);
        assertSame(n, s.getBlockScope("n").getNode());
    }

    @Test
    void getSubScope() {
        assertNull(s.getSubScope("nonExistant", "ignored"));
    }

    @Test
    void insertVariable() {
        s.openBlockScope(n);
        s.openSubScope(bn);
        s.insertVariable(n2);
        s.getBlockScope("n").getLatestSubScope().getVariable("n2");
    }

    @Test
    void reassignVariable() {
        s.openBlockScope(n);
        s.openSubScope(bn);
        s.insertVariable(n2);

        AssignNode assignNode = new AssignNode();

        assignNode.adoptChildren(
                new SelectorNode("SelectorNode"),
                new BuildNode("BuildNode")
        );

        s.getLatestBlockScope().getLatestSubScope().setVariable(new SelectorNode("SelectorNode"));
        s.reassignVariable(assignNode);
    }

    @Test
    void reassignVariable001() {
        s.openBlockScope(n);
        s.openSubScope(bn);
        s.insertVariable(n2);

        AssignNode assignNode = new AssignNode();

        assignNode.adoptChildren(
                new SelectorNode("SelectorNode"),
                new SizeNode(0, 100)
        );

        s.getLatestBlockScope().getLatestSubScope().setVariable(new SelectorNode("SelectorNode"));
        s.reassignVariable(assignNode);
    }

    @Test
    void reassignVariable002() {
        s.openBlockScope(n);
        s.openSubScope(bn);
        s.insertVariable(n2);

        AssignNode assignNode = new AssignNode();

        assignNode.adoptChildren(
                new SelectorNode("SelectorNode"),
                new SizeTypeNode("SizeTypeNode")
        );

        s.getLatestBlockScope().getLatestSubScope().setVariable(new SelectorNode("SelectorNode"));
        assertThrows(IllegalArgumentException.class, () -> s.reassignVariable(assignNode));
    }


    @Test
    void getLatestBlockScope() {
        s.openBlockScope(n);
        assertSame(s.getLatestBlockScope().getNode(), n);
    }

    @Test
    void isPredefinedOperation() {
        assertTrue(s.isPredefinedOperation("_Addition"));
        assertTrue(s.isPredefinedOperation("Multiplication"));
        assertTrue(s.isPredefinedOperation("_Subtraction"));
        assertTrue(s.isPredefinedOperation("_Addition"));
        assertTrue(s.isPredefinedOperation("_Multiplication"));
        assertTrue(s.isPredefinedOperation("_Subtraction"));
        assertTrue(s.isPredefinedOperation("_Division"));
        assertTrue(s.isPredefinedOperation("_Sigmoid"));
        assertTrue(s.isPredefinedOperation("_Tanh"));
        assertTrue(s.isPredefinedOperation("_Relu"));
        assertTrue(s.isPredefinedOperation("Transpose"));

        assertFalse(s.isPredefinedOperation("Division"));
        assertFalse(s.isPredefinedOperation("_Transpose"));
        assertFalse(s.isPredefinedOperation("Lizard"));

    }

    @Test
    void toString000() {
        s.openBlockScope(n);
        s.openSubScope(bn);
        s.insertVariable(n2);
        assertEquals(AnsiColor.removeColor(s.toString()),
                "Symbol Table:\n" +
                        "\n" +
                        "BlockScope n:\n" +
                        "\tSubScope Blueprint: \n" +
                        "\t\t\tEntry: n2 | SuperType: SIZE_TYPE | Node: [-1] SizeType n2\n" +
                        "\t\t\t\t\tSubtype: NONE | Node: NONE\n" +
                        "\n" +
                        "\n" +
                        "\n"
        );
    }
}