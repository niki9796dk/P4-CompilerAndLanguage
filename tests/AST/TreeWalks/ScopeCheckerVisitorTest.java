package AST.TreeWalks;

import AST.Nodes.AbstractNodes.Nodes.AbstractNode;
import AST.Nodes.NodeClasses.NamedNodes.BlueprintNode;
import AST.Nodes.NodeClasses.NamedNodes.ChannelDeclarationsNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.*;
import AST.Nodes.NodeClasses.NamedNodes.ProcedureCallNode;
import AST.Nodes.NodeClasses.NamedNodes.RootNode;
import AST.Nodes.SpecialNodes.UnexpectedNode;
import ScopeChecker.Exceptions.NoSuchBlockDeclaredException;
import ScopeChecker.Exceptions.NoSuchVariableDeclaredException;
import ScopeChecker.Exceptions.ScopeBoundsViolationException;
import AST.TreeWalks.Exceptions.UnexpectedNodeException;
import SymbolTableImplementation.SymbolTable;
import SymbolTableImplementation.SymbolTableInterface;
import java_cup.runtime.Symbol;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScopeCheckerVisitorTest {

    private SymbolTable symbolTable;
    private ScopeCheckerVisitor scopeCheckerVisitor;
    private BlockNode blockNode;
    private BlueprintNode blueprintNode;

    @BeforeEach
    void beforeEach() {
        this.symbolTable = new SymbolTable();
        this.scopeCheckerVisitor = new ScopeCheckerVisitor(this.symbolTable);

        this.blockNode = new BlockNode("blockNodeId");
        this.blueprintNode = new BlueprintNode();

        // Insert into symbol table
        this.symbolTable.openBlockScope(this.blockNode);
        this.symbolTable.openSubScope(this.blueprintNode);

        // Pretend to enter block and blueprint
        this.scopeCheckerVisitor.pre(0, this.blockNode);
        this.scopeCheckerVisitor.pre(0, this.blueprintNode);
    }

    @Test
    void preTestDrawBuild01() {
        BuildNode buildNode = new BuildNode("notBlockNodeId");

        assertThrows(NoSuchBlockDeclaredException.class, () -> this.scopeCheckerVisitor.pre(0, buildNode));
    }

    @Test
    void preTestDrawBuild02() {
        BlockNode otherBlockNode = new BlockNode("otherBlockNodeId");
        this.symbolTable.openBlockScope(otherBlockNode);

        BuildNode otherBuildNode = new BuildNode("otherBlockNodeId");

        // Ensures an exception is not thrown
        this.scopeCheckerVisitor.pre(0, otherBuildNode);
        assertTrue(true);
    }

    @Test
    void preTestBlock() {
        this.scopeCheckerVisitor.pre(1, this.blockNode);

        assertEquals(this.symbolTable.getBlockScope("blockNodeId"), this.scopeCheckerVisitor.getCurrentBlockScope());
    }

    @Test
    void preTestProcedure() {
        ProcedureNode procedureNode = new ProcedureNode("procedureNodeId");
        this.symbolTable.openSubScope(procedureNode);

        this.scopeCheckerVisitor.pre(1, procedureNode);

        assertEquals(this.symbolTable.getSubScope("blockNodeId" ,"PROC_procedureNodeId"), this.scopeCheckerVisitor.getCurrentSubScope());
    }

    @Test
    void preTestBlueprint() {
        BlueprintNode blueprintNode = new BlueprintNode();
        this.symbolTable.openSubScope(blueprintNode);

        this.scopeCheckerVisitor.pre(1, blueprintNode);

        assertEquals(this.symbolTable.getSubScope("blockNodeId" ,"Blueprint"), this.scopeCheckerVisitor.getCurrentSubScope());
    }

    @Test
    void preTestChannelDeclarations() {
        ChannelDeclarationsNode channelDeclarationsNode = new ChannelDeclarationsNode();
        this.symbolTable.openSubScope(channelDeclarationsNode);

        this.scopeCheckerVisitor.pre(1, channelDeclarationsNode);

        assertEquals(this.symbolTable.getSubScope("blockNodeId" ,"ChannelDeclaration"), this.scopeCheckerVisitor.getCurrentSubScope());
    }

    @Test
    void preTestProcedureCall01() {
        ProcedureCallNode procedureCallNode = new ProcedureCallNode();
        ProcedureNode procedureNode = new ProcedureNode("procedureNodeId");
        SelectorNode selectorNode = new SelectorNode("procedureNodeId");

        procedureCallNode.adoptAsFirstChild(selectorNode);

        this.symbolTable.openSubScope(procedureNode);

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

        symbolTable.openSubScope(procedureNode);

        assertThrows(ScopeBoundsViolationException.class, () -> scopeCheckerVisitor.pre(1, procedureCallNode));
    }

    // Check 'this' selector
    @Test
    void preTestSelector01() {
        MyInChannelNode node = new MyInChannelNode("in");

        SelectorNode selectorNode = new SelectorNode("this");
        selectorNode.adoptChildren(node);

        ChannelDeclarationsNode channelDeclarationsNode = new ChannelDeclarationsNode();
        this.symbolTableInterface.openSubScope(channelDeclarationsNode);

        this.scopeCheckerVisitor.pre(1, channelDeclarationsNode);

        scopeCheckerVisitor.getCurrentBlockScope().getChannelDeclarationScope().setVariable(node);

        scopeCheckerVisitor.pre(1, selectorNode);

        // Ensure it has thrown no exceptions
        assertTrue(true);
    }

    @Test
    void preTestSelector02() {
        MyInChannelNode node = new MyInChannelNode("in");

        SelectorNode selectorNode = new SelectorNode("in");

        ChannelDeclarationsNode channelDeclarationsNode = new ChannelDeclarationsNode();
        this.symbolTableInterface.openSubScope(channelDeclarationsNode);

        this.scopeCheckerVisitor.pre(1, channelDeclarationsNode);

        scopeCheckerVisitor.getCurrentBlockScope().getChannelDeclarationScope().setVariable(node);

        scopeCheckerVisitor.pre(1, selectorNode);

        // Ensure it has thrown no exceptions
        assertTrue(true);
    }

    @Test
    void preTestSelector03() {
        MyInChannelNode node = new MyInChannelNode("in");

        SelectorNode selectorNode = new SelectorNode("in");

        ChannelDeclarationsNode channelDeclarationsNode = new ChannelDeclarationsNode();
        this.symbolTableInterface.openSubScope(channelDeclarationsNode);

        this.scopeCheckerVisitor.pre(1, channelDeclarationsNode);

        assertThrows(NoSuchVariableDeclaredException.class, () -> scopeCheckerVisitor.pre(1, selectorNode));
    }

    @Test
    void preTestDoNothing() {
        RootNode rootNode = new RootNode();
        scopeCheckerVisitor.pre(1, rootNode);

        assertTrue(true);
    }

    @Test
    void preUnexpectedNode() {
        AbstractNode unexpectedNode = new UnexpectedNode("unexpectedNodeId");

        assertThrows(UnexpectedNodeException.class, () -> scopeCheckerVisitor.pre(1, unexpectedNode));
    }

    @Test
    void postTestDoNothing() {
        RootNode rootNode = new RootNode();
        scopeCheckerVisitor.post(1, rootNode);

        assertTrue(true);
    }

    @Test
    void postUnexpectedNode() {
        AbstractNode unexpectedNode = new UnexpectedNode("unexpectedNodeId");

        assertThrows(UnexpectedNodeException.class, () -> scopeCheckerVisitor.post(1, unexpectedNode));
    }
}