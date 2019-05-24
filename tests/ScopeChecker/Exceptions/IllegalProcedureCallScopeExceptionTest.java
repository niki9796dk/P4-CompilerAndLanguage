package ScopeChecker.Exceptions;

import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.DummyNode;
import CompilerExceptions.ScopeExceptions.IllegalProcedureCallScopeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IllegalProcedureCallScopeExceptionTest {
    @Test
    void noMessage() {
        assertThrows(IllegalProcedureCallScopeException.class, () -> {
            throw new IllegalProcedureCallScopeException(new DummyNode());
        });
    }

    @Test
    void message() {
        assertThrows(IllegalProcedureCallScopeException.class, () -> {
            throw new IllegalProcedureCallScopeException(new DummyNode(), "message");
        });
    }

    @Test
    void throwable() {
        assertThrows(RuntimeException.class, () -> {
            throw new IllegalProcedureCallScopeException(new DummyNode(), new RuntimeException());
        });
    }
}