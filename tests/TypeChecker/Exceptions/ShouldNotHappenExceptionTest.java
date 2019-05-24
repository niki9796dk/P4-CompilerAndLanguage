package TypeChecker.Exceptions;

import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.DummyNode;
import CompilerExceptions.TypeExceptions.ShouldNotHappenException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShouldNotHappenExceptionTest {
    @Test
    void noMessage() {
        assertThrows(ShouldNotHappenException.class, () -> {
            throw new ShouldNotHappenException(new DummyNode());
        });
    }

    @Test
    void message() {
        assertThrows(ShouldNotHappenException.class, () -> {
            throw new ShouldNotHappenException(new DummyNode(), "message");
        });
    }
}