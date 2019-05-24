package ScopeChecker.Exceptions;

import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.DummyNode;
import CompilerExceptions.ScopeExceptions.NoSuchVariableDeclaredException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NoSuchVariableDeclaredExceptionTest {
    @Test
    void noMessage() {
        assertThrows(NoSuchVariableDeclaredException.class, () -> {
            throw new NoSuchVariableDeclaredException(new DummyNode());
        });
    }

    @Test
    void message() {
        assertThrows(NoSuchVariableDeclaredException.class, () -> {
            throw new NoSuchVariableDeclaredException(new DummyNode(), "message");
        });
    }
}