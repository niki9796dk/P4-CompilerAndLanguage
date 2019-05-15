package SymbolTableImplementation;

import AST.Nodes.NodeClasses.NamedNodes.BlueprintNode;
import AST.Nodes.NodeClasses.NamedNodes.ChannelDeclarationsNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.BlockNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.BuildNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.DrawNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.ProcedureNode;
import Enums.AnsiColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BlockScopeTest {

    BlockScope b;
    BlockNode node;

    @BeforeEach
    void beforeEach(){
        node = new BlockNode("BuildNode");
        b = new BlockScope("b",node);
    }

    @Test
    void getId() {
        assertEquals(b.getId(),"b");
    }

    @Test
    void getScope() {
        Scope s = new Scope("firstScope", new DrawNode("drawNode"));
        b.getScope().setEntry("firstScope", s);
        assertSame(b.getScope().getEntry("firstScope"), s);
    }

    @Test
    void getNode() {
        assertSame(b.getNode(),node);
    }

    @Test
    void openScope() {
        b.openScope("firstScope",new BuildNode("buildNode"));
        assertEquals(b.getScope().getEntry("firstScope").getId(),"firstScope");
    }

    @Test
    void getLatestSubScope() {
        b.openScope("firstScope",new BuildNode("buildNode"));
        assertSame(b.getScope().getEntry("firstScope"),b.getLatestSubScope());
    }

    @Test
    void getChannelDeclarationScope() {
        assertNull(b.getChannelDeclarationScope());
        b.openScope("ChannelDeclaration",new ChannelDeclarationsNode());
        assertEquals(b.getChannelDeclarationScope().getId(),"ChannelDeclaration");
    }

    @Test
    void getBlueprintScope() {
        assertNull(b.getBlueprintScope());
        b.openScope("Blueprint",new BlueprintNode());
        assertEquals(b.getBlueprintScope().getId(),"Blueprint");
    }

    @Test
    void getProcedureScope() {
        assertNull(b.getProcedureScope("TEST"));
        b.openScope("PROC_TEST",new ProcedureNode("TEST"));
        assertEquals(b.getProcedureScope("TEST").getId(),"PROC_TEST");
    }

    @Test
    void toString000() {
        assertEquals(AnsiColor.removeColor(b.toString()),"BlockScope b:\n" +
                "\t\t\tEmpty");
    }
}