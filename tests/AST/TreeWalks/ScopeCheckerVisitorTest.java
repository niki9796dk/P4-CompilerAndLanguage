package AST.TreeWalks;

import AST.Nodes.AbstractNodes.Nodes.AbstractNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNode;
import AST.Nodes.NodeClasses.NamedNodes.BlueprintNode;
import AST.Nodes.NodeClasses.NamedNodes.ChannelDeclarationsNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.*;
import AST.Nodes.NodeClasses.NamedNodes.ProcedureCallNode;
import AST.Nodes.SpecialNodes.UnexpectedNode;
import AST.TreeWalks.Exceptions.NonexistentBlockException;
import AST.TreeWalks.Exceptions.ScopeBoundsViolationException;
import AST.TreeWalks.Exceptions.UnexpectedNodeException;
import SymbolTableImplementation.SymbolTable;
import SymbolTableImplementation.SymbolTableInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScopeCheckerVisitorTest {

    private SymbolTableInterface symbolTableInterface;
    private ScopeCheckerVisitor scopeCheckerVisitor;
    private BlockNode blockNode;
    private BlueprintNode blueprintNode;

    @BeforeEach
    void beforeEach() {
        this.symbolTableInterface = new SymbolTable();
        this.scopeCheckerVisitor = new ScopeCheckerVisitor(this.symbolTableInterface);

        this.blockNode = new BlockNode("blockNodeId");
        this.blueprintNode = new BlueprintNode();

        // Insert into symbol table
        this.symbolTableInterface.openBlockScope(this.blockNode);
        this.symbolTableInterface.openSubScope(this.blueprintNode);

        // Pretend to enter block and blueprint
        this.scopeCheckerVisitor.pre(0, this.blockNode);
        this.scopeCheckerVisitor.pre(0, this.blueprintNode);
    }

    @Test
    void preTestDrawBuild01() {
        BuildNode buildNode = new BuildNode("notBlockNodeId");

        assertThrows(NonexistentBlockException.class, () -> this.scopeCheckerVisitor.pre(0, buildNode));
    }

    @Test
    void preTestDrawBuild02() {
        BlockNode otherBlockNode = new BlockNode("otherBlockNodeId");
        this.symbolTableInterface.openBlockScope(otherBlockNode);

        BuildNode otherBuildNode = new BuildNode("otherBlockNodeId");

        // Ensures an exception is not thrown
        this.scopeCheckerVisitor.pre(0, otherBuildNode);
        assertTrue(true);
    }

    @Test
    void preTestBlock() {
        this.scopeCheckerVisitor.pre(1, this.blockNode);

        assertEquals(this.symbolTableInterface.getBlockScope("blockNodeId"), this.scopeCheckerVisitor.getCurrentBlockScope());
    }

    @Test
    void preTestProcedure() {
        ProcedureNode procedureNode = new ProcedureNode("procedureNodeId");
        this.symbolTableInterface.openSubScope(procedureNode);

        this.scopeCheckerVisitor.pre(1, procedureNode);

        assertEquals(this.symbolTableInterface.getSubScope("blockNodeId" ,"PROC_procedureNodeId"), this.scopeCheckerVisitor.getCurrentSubScope());
    }

    @Test
    void preTestBlueprint() {
        BlueprintNode blueprintNode = new BlueprintNode();
        this.symbolTableInterface.openSubScope(blueprintNode);

        this.scopeCheckerVisitor.pre(1, blueprintNode);

        assertEquals(this.symbolTableInterface.getSubScope("blockNodeId" ,"Blueprint"), this.scopeCheckerVisitor.getCurrentSubScope());
    }

    @Test
    void preTestChannelDeclarations() {
        ChannelDeclarationsNode channelDeclarationsNode = new ChannelDeclarationsNode();
        this.symbolTableInterface.openSubScope(channelDeclarationsNode);

        this.scopeCheckerVisitor.pre(1, channelDeclarationsNode);

        assertEquals(this.symbolTableInterface.getSubScope("blockNodeId" ,"ChannelDeclaration"), this.scopeCheckerVisitor.getCurrentSubScope());
    }

    @Test
    void preTestProcedureCall01() {
        ProcedureCallNode procedureCallNode = new ProcedureCallNode();
        ProcedureNode procedureNode = new ProcedureNode("procedureNodeId");
        SelectorNode selectorNode = new SelectorNode("procedureNodeId");

        procedureCallNode.adoptAsFirstChild(selectorNode);

        this.symbolTableInterface.openSubScope(procedureNode);

        this.scopeCheckerVisitor.pre(1, procedureCallNode);

        // Ensures an exception is not thrown
        assertTrue(true);
    }

    @Test
    void preTestProcedureCall02() {
        ProcedureCallNode procedureCallNode = new ProcedureCallNode();
        ProcedureNode procedureNode = new ProcedureNode("procedureNodeId");
        SelectorNode selectorNode = new SelectorNode("nonExistentProcedureNodeId");

        procedureCallNode.adoptAsFirstChild(selectorNode);

        symbolTableInterface.openSubScope(procedureNode);

        assertThrows(ScopeBoundsViolationException.class, () -> scopeCheckerVisitor.pre(1, procedureCallNode));
    }

    @Test
    void preTestSelector() {

    }

    @Test
    void preUnexpectedNode() {
        AbstractNode unexpectedNode = new UnexpectedNode("unexpectedNodeId");

        assertThrows(UnexpectedNodeException.class, () -> scopeCheckerVisitor.pre(1, unexpectedNode));
    }

    @Test
    void post() {
    }
}