package SemanticAnalysis.Exceptions;

import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.DummyNode;
import CompilerExceptions.SemanticExceptions.IncorrectChannelUsageException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class IncorrectChannelUsageExceptionTest {
    @Test
    void noMessage() {
        assertThrows(IncorrectChannelUsageException.class, () -> {
            throw new IncorrectChannelUsageException(new DummyNode());
        });
    }

    @Test
    void message() {
        assertThrows(IncorrectChannelUsageException.class, () -> {
            throw new IncorrectChannelUsageException(new DummyNode(), "message");
        });
    }
}