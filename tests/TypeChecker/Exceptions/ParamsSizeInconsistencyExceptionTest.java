package TypeChecker.Exceptions;

import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.DummyNode;
import CompilerExceptions.TypeExceptions.ParamsSizeInconsistencyException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ParamsSizeInconsistencyExceptionTest {
    @Test
    void noMessage() {
        assertThrows(ParamsSizeInconsistencyException.class, () -> {
            throw new ParamsSizeInconsistencyException(new DummyNode());
        });
    }

    @Test
    void message() {
        assertThrows(ParamsSizeInconsistencyException.class, () -> {
            throw new ParamsSizeInconsistencyException(new DummyNode(), "message");
        });
    }

    @Test
    void throwable() {
        Throwable toThrow = new RuntimeException();
        assertThrows(ParamsSizeInconsistencyException.class, () -> {
            throw new ParamsSizeInconsistencyException(new DummyNode(), toThrow);
        });
    }
}