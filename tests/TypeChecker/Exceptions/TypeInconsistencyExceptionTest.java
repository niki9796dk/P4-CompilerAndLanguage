package TypeChecker.Exceptions;

import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.DummyNode;
import CompilerExceptions.TypeExceptions.TypeInconsistencyException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TypeInconsistencyExceptionTest {
    @Test
    void noMessage() {
        assertThrows(TypeInconsistencyException.class, () -> {
            throw new TypeInconsistencyException(new DummyNode());
        });
    }

    @Test
    void message() {
        assertThrows(TypeInconsistencyException.class, () -> {
            throw new TypeInconsistencyException(new DummyNode(), "message");
        });
    }

    @Test
    void throwable() {
        Throwable toThrow = new RuntimeException();
        assertThrows(TypeInconsistencyException.class, () -> {
            throw new TypeInconsistencyException(new DummyNode(), toThrow);
        });
    }
}