package AST.TreeWalks;

import AST.Nodes.AbstractNodes.Nodes.AbstractNode;
import AST.Nodes.NodeClasses.NamedNodes.BlueprintNode;
import AST.Nodes.NodeClasses.NamedNodes.ChainNode;
import AST.Nodes.NodeClasses.NamedNodes.ChannelDeclarationsNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.*;
import AST.Nodes.SpecialNodes.UnexpectedNode;
import AST.TreeWalks.Exceptions.UnexpectedNodeException;
import SymbolTableImplementation.SymbolTable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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

        this.blockNode = new BlockNode("blockNodeId");
        this.blueprintNode = new BlueprintNode();

        // Insert into symbol table
        this.symbolTableInterface.openBlockScope(this.blockNode);
        this.symbolTableInterface.openSubScope(this.blueprintNode);

        // Pretend to enter block and blueprint
        this.chainCheckerVisitor.pre(0, this.blockNode);
        this.chainCheckerVisitor.pre(0, this.blueprintNode);
    }

    @Test
    void pre_chain_exception() {

    }

    @Test
    void pre_default() {

    }

    @Test
    void preUnexpectedNode() {
        AbstractNode unexpectedNode = new UnexpectedNode("unexpectedNodeId");

        assertThrows(UnexpectedNodeException.class, () -> chainCheckerVisitor.pre(1, unexpectedNode));
    }

    @Test
    void post() {
    }

    @Test
    void postUnexpectedNode() {
        AbstractNode unexpectedNode = new UnexpectedNode("unexpectedNodeId");

        assertThrows(UnexpectedNodeException.class, () -> chainCheckerVisitor.post(1, unexpectedNode));
    }
}