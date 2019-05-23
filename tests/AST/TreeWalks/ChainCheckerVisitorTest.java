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

    @Disabled
    @Test
    void pre_build() {
        BuildNode buildNode = new BuildNode("blockNodeId");
        this.chainCheckerVisitor.pre(0, buildNode);
        assertTrue(this.chainCheckerVisitor.getBuildNodes().contains(buildNode));
    }

    @Disabled
    @Test
    void pre_chain() {
        ChainNode chainNode = new ChainNode();
        BlockNode blockNode = new BlockNode("blockNodeA");
        MyInChannelNode myInChannelNode = new MyInChannelNode("in");
        MyOutChannelNode myOutChannelNode = new MyOutChannelNode("out");
        ChannelDeclarationsNode channelDeclarationsNode = new ChannelDeclarationsNode();
        channelDeclarationsNode.adoptChildren(myInChannelNode, myOutChannelNode);

        symbolTableInterface.openBlockScope(blockNode);
        symbolTableInterface.openSubScope(channelDeclarationsNode);

        BlockTypeNode blockTypeNode = new BlockTypeNode("blockNodeA");
        symbolTableInterface.getLatestBlockScope().getLatestSubScope().setVariable(blockTypeNode);


        SelectorNode selectorNode1 = new SelectorNode("blockNodeA");
        SelectorNode selectorNode2 = new SelectorNode("blockNodeA");
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

    @Disabled
    @Test
    void getBuildNodes() {
        assertNotNull(this.chainCheckerVisitor.getBuildNodes());
    }
}