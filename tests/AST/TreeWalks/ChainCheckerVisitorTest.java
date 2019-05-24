package AST.TreeWalks;

import AST.Nodes.AbstractNodes.Nodes.AbstractNode;
import AST.Nodes.NodeClasses.NamedNodes.BlueprintNode;
import AST.Nodes.NodeClasses.NamedNodes.ChainNode;
import AST.Nodes.NodeClasses.NamedNodes.ChannelDeclarationsNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.*;
import AST.Nodes.SpecialNodes.UnexpectedNode;
import AST.TreeWalks.Exceptions.UnexpectedNodeException;
import SymbolTableImplementation.SymbolTable;
import java_cup.runtime.ComplexSymbolFactory;
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

        this.blockNode = new BlockNode("blockNodeId", new ComplexSymbolFactory.Location(-1, -1));
        this.blueprintNode = new BlueprintNode(new ComplexSymbolFactory.Location(-1, -1));

        // Insert into symbol table
        this.symbolTableInterface.openBlockScope(this.blockNode);
        this.symbolTableInterface.openSubScope(this.blueprintNode);

        // Pretend to enter block and blueprint
        this.chainCheckerVisitor.pre(0, this.blockNode);
        this.chainCheckerVisitor.pre(0, this.blueprintNode);
    }

    @Disabled
    @Test
    void pre_chain() {
        ChainNode chainNode = new ChainNode(new ComplexSymbolFactory.Location(-1, -1));
        BlockNode blockNode = new BlockNode("blockNodeA", new ComplexSymbolFactory.Location(-1, -1));
        MyInChannelNode myInChannelNode = new MyInChannelNode("in", new ComplexSymbolFactory.Location(-1, -1));
        MyOutChannelNode myOutChannelNode = new MyOutChannelNode("out", new ComplexSymbolFactory.Location(-1, -1));
        ChannelDeclarationsNode channelDeclarationsNode = new ChannelDeclarationsNode(new ComplexSymbolFactory.Location(-1, -1));
        channelDeclarationsNode.adoptChildren(myInChannelNode, myOutChannelNode);

        symbolTableInterface.openBlockScope(blockNode);
        symbolTableInterface.openSubScope(channelDeclarationsNode);

        BlockTypeNode blockTypeNode = new BlockTypeNode("blockNodeA", new ComplexSymbolFactory.Location(-1, -1));
        symbolTableInterface.getLatestBlockScope().getLatestSubScope().setVariable(blockTypeNode);


        SelectorNode selectorNode1 = new SelectorNode("blockNodeA", new ComplexSymbolFactory.Location(-1, -1));
        SelectorNode selectorNode2 = new SelectorNode("blockNodeA", new ComplexSymbolFactory.Location(-1, -1));
        selectorNode1.adoptChildren(blockTypeNode);
        selectorNode2.adoptChildren(blockTypeNode);

        chainNode.adoptChildren(selectorNode1, selectorNode2);

        symbolTableInterface.openBlockScope(blockNode);

        this.chainCheckerVisitor.pre(0, chainNode);

        assertTrue(true);
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