package TypeChecker.Exceptions;

import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.DummyNode;
import CompilerExceptions.TypeExceptions.ParamsTypeInconsistencyException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ParamsTypeInconsistencyExceptionTest {
    @Test
    void noMessage() {
        assertThrows(ParamsTypeInconsistencyException.class, () -> {
            throw new ParamsTypeInconsistencyException(new DummyNode());
        });
    }

    @Test
    void message() {
        assertThrows(ParamsTypeInconsistencyException.class, () -> {
            throw new ParamsTypeInconsistencyException(new DummyNode(), "message");
        });
    }

    @Test
    void throwable() {
        Throwable toThrow = new RuntimeException();
        assertThrows(ParamsTypeInconsistencyException.class, () -> {
            throw new ParamsTypeInconsistencyException(new DummyNode(), toThrow);
        });
    }
}