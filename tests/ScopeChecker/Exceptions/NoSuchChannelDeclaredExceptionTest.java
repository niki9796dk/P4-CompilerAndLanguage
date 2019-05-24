package ScopeChecker.Exceptions;

import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.DummyNode;
import CompilerExceptions.ScopeExceptions.NoSuchChannelDeclaredException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NoSuchChannelDeclaredExceptionTest {
    @Test
    void noMessage() {
        assertThrows(NoSuchChannelDeclaredException.class, () -> {
            throw new NoSuchChannelDeclaredException(new DummyNode());
        });
    }

    @Test
    void message() {
        assertThrows(NoSuchChannelDeclaredException.class, () -> {
            throw new NoSuchChannelDeclaredException(new DummyNode(), "message");
        });
    }
}