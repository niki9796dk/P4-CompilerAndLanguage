package AST.TreeWalks;

import AST.Nodes.NodeClasses.NamedNodes.BlueprintNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.BlockNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.BuildNode;
import SemanticAnalysis.FlowChecker;
import SymbolTableImplementation.SymbolTable;
import SymbolTableImplementation.SymbolTableInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SemanticAnalysisVisitorTest {

    // Fields:
    private SymbolTableInterface symbolTableInterface;
    private SemanticAnalysisVisitor semanticAnalysisVisitor;
    private BlockNode blockNode;
    private BlueprintNode blueprintNode;

    @BeforeEach
    void beforeEach() {
        this.symbolTableInterface = new SymbolTable();
        this.semanticAnalysisVisitor = new SemanticAnalysisVisitor(this.symbolTableInterface);

        this.blockNode = new BlockNode("blockNodeId");
        this.blueprintNode = new BlueprintNode();

        // Insert into symbol table
        this.symbolTableInterface.openBlockScope(this.blockNode);
        this.symbolTableInterface.openSubScope(this.blueprintNode);

        // Pretend to enter block and blueprint
        this.semanticAnalysisVisitor.pre(0, this.blockNode);
        this.semanticAnalysisVisitor.pre(0, this.blueprintNode);
    }

    @Test
    void pre_build() {
        BuildNode buildNode = new BuildNode("build");
        this.semanticAnalysisVisitor.pre(0, buildNode);
        assertTrue(this.semanticAnalysisVisitor.getBuildNodes().contains(buildNode));
    }

    @Test
    void pre_chain() {
        this.semanticAnalysisVisitor.pre(0, this.blueprintNode);
        assertTrue(true);
    }

    @Test
    void pre_chain_exception() {

    }

    @Test
    void pre_default() {

    }

    @Test
    void post() {
    }

    @Test
    void getFlowChecker() {
        assertTrue(semanticAnalysisVisitor.getFlowChecker() != null);
    }

    @Test
    void getBuildNodes() {
        assertTrue(semanticAnalysisVisitor.getBuildNodes() != null);
    }
}