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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SymbolTableTest {

    // Fields
    private SymbolTable s;
    private BlockNode n;
    private NamedIdNode n2;
    private NamedIdNode n3;
    private BlueprintNode bn;

    /**
     * Adds a block table to the symbol table
     * Param: The node to add to the symbol table.
     */
    @BeforeEach
    void beforeEach() {
        this.s = new SymbolTable();
        this.n = new BlockNode("n");
        this.n2 = new SizeTypeNode("n2");
        this.n3 = new SizeTypeNode("n3");
        this.bn = new BlueprintNode();
    }

    @Test
    void getMainBlocks() {
        assertNull(this.s.getMainBlocks());
    }

    @Test
    void setMainBlocks() {
        this.s.setMainBlocks(new ArrayList<>());
        assertNotNull(this.s.getMainBlocks());
    }

    @Test
    void openBlockScope() {
        this.s.openBlockScope(this.n);
    }

    @Test
    void openSubScope() {
        this.s.openBlockScope(this.n);
        this.s.openSubScope(this.bn);

        ChannelDeclarationsNode cdNode = new ChannelDeclarationsNode();
        ProcedureNode pNode = new ProcedureNode("pNode");
        SizeNode sNode = new SizeNode(0, 2);

        this.s.openSubScope(cdNode);
        this.s.openSubScope(pNode);
        assertThrows(IllegalArgumentException.class, () -> s.openSubScope(sNode));

    }

    @Test
    void getBlockScope() {
        this.s.openBlockScope(this.n);
        assertSame(this.n, this.s.getBlockScope("n").getNode());
    }

    @Test
    void insertVariable() {
        this.s.openBlockScope(this.n);
        this.s.openSubScope(this.bn);
        this.s.insertVariable(this.n2);
        this.s.getBlockScope("n").getLatestSubScope().getVariable("n2");
    }

    @Test
    void reassignVariable() {
        this.s.openBlockScope(this.n);
        this.s.openSubScope(this.bn);
        this.s.insertVariable(this.n2);

        AssignNode assignNode = new AssignNode();

        assignNode.adoptChildren(
                new SelectorNode("SelectorNode"),
                new BuildNode("BuildNode")
        );

        this.s.getLatestBlockScope().getLatestSubScope().setVariable(new SelectorNode("SelectorNode"));
        this.s.reassignVariable(assignNode);
    }

    @Test
    void reassignVariable001() {
        this.s.openBlockScope(this.n);
        this.s.openSubScope(this.bn);
        this.s.insertVariable(this.n2);

        AssignNode assignNode = new AssignNode();

        assignNode.adoptChildren(
                new SelectorNode("SelectorNode"),
                new SizeNode(0, 100)
        );

        this.s.getLatestBlockScope().getLatestSubScope().setVariable(new SelectorNode("SelectorNode"));
        this.s.reassignVariable(assignNode);
    }

    @Test
    void reassignVariable002() {
        this.s.openBlockScope(this.n);
        this.s.openSubScope(this.bn);
        this.s.insertVariable(this.n2);

        AssignNode assignNode = new AssignNode();

        assignNode.adoptChildren(
                new SelectorNode("SelectorNode"),
                new SizeTypeNode("SizeTypeNode")
        );

        s.getLatestBlockScope().getLatestSubScope().setVariable(new SelectorNode("SelectorNode"));
        assertThrows(IllegalArgumentException.class, () -> s.reassignVariable(assignNode));
    }

    @Test
    void getSubScope() {
        assertEquals(null, s.getSubScope("", ""));
    }



    @Test
    void getLatestBlockScope() {
        this.s.openBlockScope(this.n);
        assertSame(this.s.getLatestBlockScope().getNode(), this.n);
    }

    @Test
    void isPredefinedOperation() {
        assertTrue(this.s.isPredefinedOperation("Multiplication"));
        assertTrue(this.s.isPredefinedOperation("_Addition"));
        assertTrue(this.s.isPredefinedOperation("_Multiplication"));
        assertTrue(this.s.isPredefinedOperation("_Subtraction"));
        assertTrue(this.s.isPredefinedOperation("_Division"));
        assertTrue(this.s.isPredefinedOperation("_Sigmoid"));
        assertTrue(this.s.isPredefinedOperation("_Tanh"));
        assertTrue(this.s.isPredefinedOperation("_Relu"));
        assertTrue(this.s.isPredefinedOperation("Transpose"));

        assertFalse(this.s.isPredefinedOperation("Division"));
        assertFalse(this.s.isPredefinedOperation("_Transpose"));
        assertFalse(this.s.isPredefinedOperation("Lizard"));

    }

    @Test
    void toString000() {
        this.s.openBlockScope(this.n);
        this.s.openSubScope(this.bn);
        this.s.insertVariable(this.n2);
        assertEquals(AnsiColor.removeColor(this.s.toString()),
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