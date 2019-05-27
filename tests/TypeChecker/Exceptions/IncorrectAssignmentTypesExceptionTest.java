package TypeChecker.Exceptions;

import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.DummyNode;
import CompilerExceptions.TypeExceptions.IncorrectAssignmentTypesException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class IncorrectAssignmentTypesExceptionTest {
    @Test
    void noMessage() {
        assertThrows(IncorrectAssignmentTypesException.class, () -> {
            throw new IncorrectAssignmentTypesException(new DummyNode());
        });
    }

    @Test
    void message() {
        assertThrows(IncorrectAssignmentTypesException.class, () -> {
            throw new IncorrectAssignmentTypesException(new DummyNode(), "message");
        });
    }

    @Test
    void throwable() {
        Throwable toThrow = new RuntimeException();
        assertThrows(IncorrectAssignmentTypesException.class, () -> {
            throw new IncorrectAssignmentTypesException(new DummyNode(), toThrow);
        });
    }
}