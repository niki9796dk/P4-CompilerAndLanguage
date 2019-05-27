package AST.TreeWalks;

import AST.Nodes.AbstractNodes.Nodes.AbstractNode;
import AST.Nodes.NodeClasses.NamedNodes.BlueprintNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.BlockNode;
import AST.Nodes.SpecialNodes.UnexpectedNode;
import CompilerExceptions.UnexpectedNodeException;
import SymbolTableImplementation.SymbolTable;
import java_cup.runtime.ComplexSymbolFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ChainCheckerVisitorTest {

    // Fields:
    private SymbolTable symbolTableInterface;
    private ChainCheckerVisitor chainCheckerVisitor;
    private BlockNode blockNode;
    private BlueprintNode blueprintNode;

    @BeforeEach
    void beforeEach() {
        this.symbolTableInterface = new SymbolTable();
        this.chainCheckerVisitor = new ChainCheckerVisitor(this.symbolTableInterface);

        this.blockNode = new BlockNode("blockNodeId", new ComplexSymbolFactory.Location(-1, -1));
        this.blueprintNode = new BlueprintNode(new ComplexSymbolFactory.Location(-1, -1));

        // Insert into symbol table
        this.symbolTableInterface.openBlockScope(this.blockNode);
        this.symbolTableInterface.openSubScope(this.blueprintNode);

        // Pretend to enter block and blueprint
        this.chainCheckerVisitor.pre(0, this.blockNode);
        this.chainCheckerVisitor.pre(0, this.blueprintNode);
    }

    @Test
    void preUnexpectedNode() {
        AbstractNode unexpectedNode = new UnexpectedNode("unexpectedNodeId");

        assertThrows(UnexpectedNodeException.class, () -> chainCheckerVisitor.pre(1, unexpectedNode));
    }

    @Test
    void postUnexpectedNode() {
        AbstractNode unexpectedNode = new UnexpectedNode("unexpectedNodeId");

        assertThrows(UnexpectedNodeException.class, () -> chainCheckerVisitor.post(1, unexpectedNode));
    }
}