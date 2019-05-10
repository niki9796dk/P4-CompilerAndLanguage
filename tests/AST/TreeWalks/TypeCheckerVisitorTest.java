package AST.TreeWalks;

import AST.Nodes.AbstractNodes.Nodes.AbstractNode;
import AST.Nodes.NodeClasses.NamedNodes.AssignNode;
import AST.Nodes.NodeClasses.NamedNodes.BlueprintNode;
import AST.Nodes.NodeClasses.NamedNodes.ChainNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.BlockNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.BlockTypeNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.BuildNode;
import AST.Nodes.SpecialNodes.UnexpectedNode;
import AST.TreeWalks.Exceptions.UnexpectedNodeException;
import SymbolTableImplementation.SymbolTable;
import SymbolTableImplementation.SymbolTableInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TypeCheckerVisitorTest {

    private TypeCheckerVisitor typeCheckerVisitor;
    private SymbolTableInterface symbolTableInterface;

    @BeforeEach
    void makeTypeCheckerVisitor() {
        symbolTableInterface = new SymbolTable();
        typeCheckerVisitor = new TypeCheckerVisitor(symbolTableInterface);
    }

    @Test
    void preUnexpectedNode() {
        AbstractNode unexpectedNode = new UnexpectedNode("unexpectedNodeId");

        assertThrows(UnexpectedNodeException.class, () -> typeCheckerVisitor.pre(1, unexpectedNode));
    }

    @Test
    void post() {
    }

    @Test
    void postUnexpectedNode() {
        AbstractNode unexpectedNode = new UnexpectedNode("unexpectedNodeId");

        assertThrows(UnexpectedNodeException.class, () -> typeCheckerVisitor.post(1, unexpectedNode));
    }
}