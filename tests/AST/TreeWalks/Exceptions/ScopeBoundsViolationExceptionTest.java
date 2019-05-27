package AST.TreeWalks.Exceptions;

import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.DummyNode;
import CompilerExceptions.ScopeExceptions.ScopeBoundsViolationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ScopeBoundsViolationExceptionTest {

    @Test
    void noMessage() {
        assertThrows(ScopeBoundsViolationException.class, () -> {
            throw new ScopeBoundsViolationException(new DummyNode());
        });
    }

    @Test
    void message() {
        assertThrows(ScopeBoundsViolationException.class, () -> {
            throw new ScopeBoundsViolationException(new DummyNode(), "message");
        });
    }

    @Test
    void throwable() {
        assertThrows(RuntimeException.class, () -> {
            throw new ScopeBoundsViolationException(new DummyNode(), new RuntimeException());
        });
    }
}