package SemanticAnalysis.Exceptions;

import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.DummyNode;
import CompilerExceptions.SemanticExceptions.SemanticProblemException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class SemanticProblemExceptionTest {

    @Test
    void noMessage() {
        assertThrows(SemanticProblemException.class, () -> {
            throw new SemanticProblemException(new DummyNode());
        });
    }

    @Test
    void message() {
        assertThrows(SemanticProblemException.class, () -> {
            throw new SemanticProblemException(new DummyNode(), "message");
        });
    }
}