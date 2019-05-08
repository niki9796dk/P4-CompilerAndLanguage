package AST.TreeWalks;

import AST.Nodes.NodeClasses.NamedNodes.BlueprintNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.BlockNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.BlockTypeNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.BuildNode;
import AST.TreeWalks.Exceptions.NonexistentBlockException;
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
        assertDoesNotThrow(() -> scopeCheckerVisitor.pre(0, otherBuildNode));
    }

    @Test
    void preTest() {
    }

    @Test
    void post() {
    }
}