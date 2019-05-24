package SemanticAnalysis.Exceptions;

import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.DummyNode;
import CompilerExceptions.SemanticExceptions.BuildRecursionException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BuildRecursionExceptionTest {
    @Test
    void noMessage() {
        assertThrows(BuildRecursionException.class, () -> {
            throw new BuildRecursionException(new DummyNode());
        });
    }

    @Test
    void message() {
        assertThrows(BuildRecursionException.class, () -> {
            throw new BuildRecursionException(new DummyNode(), "message");
        });
    }
}