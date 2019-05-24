package SymbolTableImplementation;

import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;
import AST.Nodes.NodeClasses.NamedNodes.AssignNode;
import AST.Nodes.NodeClasses.NamedNodes.BlueprintNode;
import AST.Nodes.NodeClasses.NamedNodes.ChannelDeclarationsNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.*;
import AST.Nodes.NodeClasses.NamedNodes.SizeNode;
import Enums.AnsiColor;
import TypeChecker.TypeSystem;
import java_cup.runtime.ComplexSymbolFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SymbolTableTest {

    // Fields
    private SymbolTable s;
    private TypeSystem t;
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
        this.t = new TypeSystem(s);
        this.n = new BlockNode("n", new ComplexSymbolFactory.Location(-1, -1));
        this.n2 = new SizeTypeNode("n2", new ComplexSymbolFactory.Location(-1, -1));
        this.n3 = new SizeTypeNode("n3", new ComplexSymbolFactory.Location(-1, -1));
        this.bn = new BlueprintNode(new ComplexSymbolFactory.Location(-1, -1));
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

        ChannelDeclarationsNode cdNode = new ChannelDeclarationsNode(new ComplexSymbolFactory.Location(-1, -1));
        ProcedureNode pNode = new ProcedureNode("pNode", new ComplexSymbolFactory.Location(-1, -1));
        SizeNode sNode = new SizeNode(0, 2, new ComplexSymbolFactory.Location(-1, -1));

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

        AssignNode assignNode = new AssignNode(new ComplexSymbolFactory.Location(-1, -1));

        assignNode.adoptChildren(
                new SelectorNode("SelectorNode", new ComplexSymbolFactory.Location(-1, -1)),
                new BuildNode("BuildNode", new ComplexSymbolFactory.Location(-1, -1))
        );

        this.s.getLatestBlockScope().getLatestSubScope().setVariable(new SelectorNode("SelectorNode", new ComplexSymbolFactory.Location(-1, -1)));
        this.s.reassignVariable(assignNode);
    }

    @Test
    void reassignVariable001() {
        this.s.openBlockScope(this.n);
        this.s.openSubScope(this.bn);
        this.s.insertVariable(this.n2);

        AssignNode assignNode = new AssignNode(new ComplexSymbolFactory.Location(-1, -1));

        assignNode.adoptChildren(
                new SelectorNode("SelectorNode", new ComplexSymbolFactory.Location(-1, -1)),
                new SizeNode(0, 100, new ComplexSymbolFactory.Location(-1, -1))
        );

        this.s.getLatestBlockScope().getLatestSubScope().setVariable(new SelectorNode("SelectorNode", new ComplexSymbolFactory.Location(-1, -1)));
        this.s.reassignVariable(assignNode);
    }

    @Test
    void reassignVariable002() {
        this.s.openBlockScope(this.n);
        this.s.openSubScope(this.bn);
        this.s.insertVariable(this.n2);

        AssignNode assignNode = new AssignNode(new ComplexSymbolFactory.Location(-1, -1));

        assignNode.adoptChildren(
                new SelectorNode("SelectorNode", new ComplexSymbolFactory.Location(-1, -1)),
                new SizeTypeNode("SizeTypeNode", new ComplexSymbolFactory.Location(-1, -1))
        );

        s.getLatestBlockScope().getLatestSubScope().setVariable(new SelectorNode("SelectorNode", new ComplexSymbolFactory.Location(-1, -1)));
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
        assertTrue(this.t.isPredefinedOperation("Multiplication"));
        assertTrue(this.t.isPredefinedOperation("_Addition"));
        assertTrue(this.t.isPredefinedOperation("_Multiplication"));
        assertTrue(this.t.isPredefinedOperation("_Subtraction"));
        assertTrue(this.t.isPredefinedOperation("_Division"));
        assertTrue(this.t.isPredefinedOperation("_Sigmoid"));
        assertTrue(this.t.isPredefinedOperation("_Tanh"));
        assertTrue(this.t.isPredefinedOperation("_Relu"));
        assertTrue(this.t.isPredefinedOperation("Transpose"));

        assertFalse(this.t.isPredefinedOperation("Division"));
        assertFalse(this.t.isPredefinedOperation("_Transpose"));
        assertFalse(this.t.isPredefinedOperation("Lizard"));
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