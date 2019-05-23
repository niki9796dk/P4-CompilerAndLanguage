package AST.TreeWalks;

import AST.Nodes.AbstractNodes.Nodes.AbstractNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;
import AST.Nodes.NodeClasses.NamedNodes.AssignNode;
import AST.Nodes.NodeClasses.NamedNodes.BlueprintNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.BlockNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.BlockTypeNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.BlueprintTypeNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.BuildNode;
import AST.Nodes.SpecialNodes.UnexpectedNode;
import ScopeChecker.Exceptions.ScopeBoundsViolationException;
import AST.TreeWalks.Exceptions.UnexpectedNodeException;
import SymbolTableImplementation.BlockScope;
import java_cup.runtime.ComplexSymbolFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SymbolTableVisitorTest {

    private SymbolTableVisitor symbolTableVisitor;
    private AbstractNode blockNode;
    private AbstractNode blueprintNode;

    @BeforeEach
    void makeSymbolTableVisitor() {
        symbolTableVisitor = new SymbolTableVisitor();

        blockNode = new BlockNode("blockId", new ComplexSymbolFactory.Location(-1, -1));
        symbolTableVisitor.getSymbolTableInterface().openBlockScope((BlockNode) blockNode);

        blueprintNode = new BlueprintNode(new ComplexSymbolFactory.Location(-1, -1));
        symbolTableVisitor.getSymbolTableInterface().openSubScope((BlueprintNode) blueprintNode);
    }

    @Test
    void preTestOpenBlockScope() {
        symbolTableVisitor.pre(0, blockNode);
        assertEquals(symbolTableVisitor.getSymbolTableInterface().getBlockScope("blockId").getNode(), blockNode);
    }

    @Test
    void preTestOpenSubScope() {
        symbolTableVisitor.pre(0, blueprintNode);

        assertEquals(symbolTableVisitor.getSymbolTableInterface().getSubScope("blockId", BlockScope.BLUEPRINT).getNode(), blueprintNode);
    }

    @Test
    void preTestCheckIfVariableIsDefinedAndInsert01() {
        AbstractNode blueprintTypeNode = new BlueprintTypeNode("blueprintTypeId", new ComplexSymbolFactory.Location(-1, -1));
        symbolTableVisitor.pre(0, blueprintTypeNode);

        assertEquals(symbolTableVisitor.getSymbolTableInterface().getBlockScope("blockId").getSubscope("Blueprint").getVariable("blueprintTypeId").getNode(), blueprintTypeNode);
    }

    // Test that if variable is already defined it should throw an error
    @Test
    void preTestCheckIfVariableIsDefinedAndInsert02() {
        BlueprintTypeNode blueprintTypeNode = new BlueprintTypeNode("blueprintTypeId", new ComplexSymbolFactory.Location(-1, -1));
        symbolTableVisitor.pre(0, blueprintTypeNode);

        assertThrows(ScopeBoundsViolationException.class, () -> symbolTableVisitor.pre(0, blueprintTypeNode));
    }

    @Test
    void preTestDefaultCase() {
        AbstractNode temporaryNode = new UnexpectedNode("unexpectedNode");

        assertThrows(UnexpectedNodeException.class, () -> symbolTableVisitor.pre(0, temporaryNode));
    }

    @Test
    void preUnexpectedNode() {
        AbstractNode unexpectedNode = new UnexpectedNode("unexpectedNodeId");

        assertThrows(UnexpectedNodeException.class, () -> symbolTableVisitor.pre(1, unexpectedNode));
    }

    @Test
    void postUnexpectedNode() {
        AbstractNode unexpectedNode = new UnexpectedNode("unexpectedNodeId");

        assertThrows(UnexpectedNodeException.class, () -> symbolTableVisitor.post(1, unexpectedNode));
    }

    @Test
    void postAssign() {
        NamedIdNode leftSide = new BlockTypeNode("blockTypeId", new ComplexSymbolFactory.Location(-1, -1));
        NamedNode rightSideBefore = new BlueprintNode(new ComplexSymbolFactory.Location(-1, -1));
        NamedNode assignNodeBefore = new AssignNode(new ComplexSymbolFactory.Location(-1, -1));
        leftSide.makeSibling(rightSideBefore);
        assignNodeBefore.adoptAsFirstChild(leftSide);

        symbolTableVisitor.pre(0, leftSide);

        symbolTableVisitor.pre(0, assignNodeBefore);


        NamedNode rightSideAfter = new BuildNode("buildNode", new ComplexSymbolFactory.Location(-1, -1));
        NamedNode assignNodeAfter = new AssignNode(new ComplexSymbolFactory.Location(-1, -1));
        leftSide.orphan();
        leftSide.makeSibling(rightSideAfter);
        assignNodeAfter.adoptAsFirstChild(leftSide);

        symbolTableVisitor.post(0, assignNodeAfter);

        assertEquals(symbolTableVisitor.getSymbolTableInterface().getBlockScope("blockId").getSubscope("Blueprint").getVariable("blockTypeId").getSubType(1), rightSideAfter);
    }
}