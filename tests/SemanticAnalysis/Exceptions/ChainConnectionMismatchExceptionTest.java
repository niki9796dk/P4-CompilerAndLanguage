package SemanticAnalysis.Exceptions;

import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.DummyNode;
import CompilerExceptions.SemanticExceptions.ChainConnectionMismatchException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ChainConnectionMismatchExceptionTest {
    @Test
    void noMessage() {
        assertThrows(ChainConnectionMismatchException.class, () -> {
            throw new ChainConnectionMismatchException(new DummyNode());
        });
    }

    @Test
    void message() {
        assertThrows(ChainConnectionMismatchException.class, () -> {
            throw new ChainConnectionMismatchException(new DummyNode(), "message");
        });
    }
}