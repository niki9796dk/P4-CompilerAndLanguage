package SymbolTableImplementation;

import AST.Nodes.AbstractNodes.Node;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;
import AST.Nodes.NodeClasses.NamedNodes.AssignNode;
import AST.Nodes.NodeClasses.NamedNodes.BlueprintNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.BlockTypeNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.BuildNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.SelectorNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.SizeTypeNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SymbolTableTest {

    SymbolTable s;
    NamedIdNode n;
    NamedIdNode n2;
    NamedIdNode n3;
    BlueprintNode bn;

    /**
     * Adds a block table to the symbol table
     * Param: The node to add to the symbol table.
     */
    @BeforeEach
    void beforeEach(){
        s = new SymbolTable();
        n = new SizeTypeNode("n");
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
    }

    @Test
    void getBlockScope() {
        s.openBlockScope(n);
        assertSame(n,s.getBlockScope("n").getNode());
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

        s.getLatestBlockScope().getLatestSubScope().setVariable( new SelectorNode("SelectorNode"));
        s.reassignVariable(assignNode);
    }

    @Test
    void getLatestBlockScope() {
        s.openBlockScope(n);
        assertSame(s.getLatestBlockScope().getNode(),n);
    }

    @Test
    void isPredefinedOperation() {
        assertTrue(s.isPredefinedOperation("Addition"));
        assertTrue(s.isPredefinedOperation("Multiplication"));
        assertTrue(s.isPredefinedOperation("Subtraction"));
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
        System.out.println(s.toString());
    }
}