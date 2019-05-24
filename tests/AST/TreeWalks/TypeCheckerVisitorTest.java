package AST.TreeWalks;

import AST.Nodes.AbstractNodes.Nodes.AbstractNode;
import AST.Nodes.SpecialNodes.UnexpectedNode;
import CompilerExceptions.UnexpectedNodeException;
import SymbolTableImplementation.SymbolTable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TypeCheckerVisitorTest {

    private TypeCheckerVisitor typeCheckerVisitor;
    private SymbolTable symbolTableInterface;

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