package AST.TreeWalks;

import AST.Nodes.NodeClasses.NamedNodes.BlueprintNode;
import AST.Nodes.NodeClasses.NamedNodes.ChannelDeclarationsNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.*;
import AST.Nodes.NodeClasses.NamedNodes.ProcedureCallNode;
import AST.TreeWalks.Exceptions.NonexistentBlockException;
import AST.TreeWalks.Exceptions.ScopeBoundsViolationException;
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
        symbolTableInterface = new SymbolTable();
        scopeCheckerVisitor = new ScopeCheckerVisitor(symbolTableInterface);

        blockNode = new BlockNode("blockNodeId");
        blueprintNode = new BlueprintNode();

        // Insert into symbol table
        symbolTableInterface.openBlockScope(blockNode);
        symbolTableInterface.openSubScope(blueprintNode);

        // Pretend to enter block and blueprint
        scopeCheckerVisitor.pre(0, blockNode);
        scopeCheckerVisitor.pre(0, blueprintNode);
    }

    @Test
    void preTestDrawBuild01() {
        BuildNode buildNode = new BuildNode("notBlockNodeId");

        assertThrows(NonexistentBlockException.class, () -> scopeCheckerVisitor.pre(0, buildNode));
    }

    @Test
    void preTestDrawBuild02() {
        BlockNode otherBlockNode = new BlockNode("otherBlockNodeId");
        symbolTableInterface.openBlockScope(otherBlockNode);

        BuildNode otherBuildNode = new BuildNode("otherBlockNodeId");

        // Ensures an exception is not thrown
        scopeCheckerVisitor.pre(0, otherBuildNode);
        assertTrue(true);
    }

    @Test
    void preTestBlock() {
        scopeCheckerVisitor.pre(1, blockNode);

        assertEquals(symbolTableInterface.getBlockScope("blockNodeId"), scopeCheckerVisitor.getCurrentBlockScope());
    }

    @Test
    void preTestProcedure() {
        ProcedureNode procedureNode = new ProcedureNode("procedureNodeId");
        symbolTableInterface.openSubScope(procedureNode);

        scopeCheckerVisitor.pre(1, procedureNode);

        assertEquals(symbolTableInterface.getSubScope("blockNodeId" ,"PROC_procedureNodeId"), scopeCheckerVisitor.getCurrentSubScope());
    }

    @Test
    void preTestBlueprint() {
        BlueprintNode blueprintNode = new BlueprintNode();
        symbolTableInterface.openSubScope(blueprintNode);

        scopeCheckerVisitor.pre(1, blueprintNode);

        assertEquals(symbolTableInterface.getSubScope("blockNodeId" ,"Blueprint"), scopeCheckerVisitor.getCurrentSubScope());
    }

    @Test
    void preTestChannelDeclarations() {
        ChannelDeclarationsNode channelDeclarationsNode = new ChannelDeclarationsNode();
        symbolTableInterface.openSubScope(channelDeclarationsNode);

        scopeCheckerVisitor.pre(1, channelDeclarationsNode);

        assertEquals(symbolTableInterface.getSubScope("blockNodeId" ,"ChannelDeclaration"), scopeCheckerVisitor.getCurrentSubScope());
    }

    @Test
    void preTestProcedureCall01() {
        ProcedureCallNode procedureCallNode = new ProcedureCallNode();
        ProcedureNode procedureNode = new ProcedureNode("procedureNodeId");
        SelectorNode selectorNode = new SelectorNode("procedureNodeId");

        procedureCallNode.adoptAsFirstChild(selectorNode);

        symbolTableInterface.openSubScope(procedureNode);

        scopeCheckerVisitor.pre(1, procedureCallNode);

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
    void post() {
    }
}