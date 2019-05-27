package AST.TreeWalks.Exceptions;

import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.DummyNode;
import CompilerExceptions.ScopeExceptions.NoSuchBlockDeclaredException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class NoSuchBlockDeclaredExceptionTest {
    @Test
    void noMessage() {
        assertThrows(NoSuchBlockDeclaredException.class, () -> {
            throw new NoSuchBlockDeclaredException(new DummyNode());
        });
    }

    @Test
    void message() {
        assertThrows(NoSuchBlockDeclaredException.class, () -> {
            throw new NoSuchBlockDeclaredException(new DummyNode(), "message");
        });
    }

    @Test
    void throwable() {
        assertThrows(RuntimeException.class, () -> {
            throw new NoSuchBlockDeclaredException(new DummyNode(), new RuntimeException());
        });
    }
}