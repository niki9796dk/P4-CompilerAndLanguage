package SymbolTableImplementation;

import AST.Nodes.AbstractNodes.Node;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;
import AST.Nodes.NodeClasses.NamedNodes.ChainNode;
import AST.Nodes.NodeClasses.NamedNodes.AssignNode;
import AST.Nodes.NodeClasses.NamedNodes.GroupNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.BlockNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.BlockTypeNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.ProcedureNode;
import AST.Nodes.NodeClasses.NamedNodes.ParamsNode;
import AST.Nodes.NodeClasses.NamedNodes.SizeNode;
import Enums.AnsiColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScopeTest {

    // Fields
    private NamedNode n;
    private Scope s;

    @BeforeEach
    void beforeEach() {
        this.n = new GroupNode();
        this.s = new Scope("id", this.n);
    }

    @Test
    void getId() {
        assertEquals("id", this.s.getId());
    }

    @Test
    void setVariable01() {
        s.setVariable(new BlockNode("blocknode_id"));
    }

    @Test
    void setVariable02() {
        ProcedureNode procedureNode = new ProcedureNode("proc");
        ParamsNode paramsNode = new ParamsNode();
        SizeNode sizeNode = new SizeNode(1, 2);
        procedureNode.adoptChildren(paramsNode);
        paramsNode.adoptChildren(sizeNode);
        this.s.setVariable(sizeNode);
        assertEquals(sizeNode, s.getVariable(sizeNode).getNode());
    }

    @Test
    void getVariable01() {
        BlockNode node = new BlockNode("blocknode_id");
        this.s.setVariable(node);
        assertSame(this.s.getVariable("blocknode_id").getNode(), node);
    }

    @Test
    void getVariable02() {
        Node node = new BlockNode("blocknode_id");
        this.s.setVariable((NamedIdNode) node);
        assertSame(this.s.getVariable(node).getNode(), node);
    }

    @Test
    void getVariable03() {
        Node node = new ChainNode();
        assertThrows(IllegalArgumentException.class, () -> this.s.getVariable(node));
    }

    @Test
    void getVariable04() {
        NamedIdNode node = new BlockTypeNode("id");
        this.s.setVariable(node);
        assertSame(this.s.getVariable(node).getNode(), node);

    }

    @Test
    void getNode() {
        assertSame(s.getNode(), this.n);
    }

    @Test
    void toString000() {
        assertEquals(this.s.toString(), AnsiColor.BLUE + "\tSubScope id" + AnsiColor.RESET + ": \n" + AnsiColor.RED + "\t\t\tEmpty" + AnsiColor.RESET);
    }
}