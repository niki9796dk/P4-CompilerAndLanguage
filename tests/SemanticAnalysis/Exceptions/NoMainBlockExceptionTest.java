package SemanticAnalysis.Exceptions;

import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.DummyNode;
import CompilerExceptions.SemanticExceptions.NoMainBlockException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class NoMainBlockExceptionTest {
    @Test
    void noMessage() {
        assertThrows(NoMainBlockException.class, () -> {
            throw new NoMainBlockException(new DummyNode());
        });
    }

    @Test
    void message() {
        assertThrows(NoMainBlockException.class, () -> {
            throw new NoMainBlockException(new DummyNode(), "message");
        });
    }
}