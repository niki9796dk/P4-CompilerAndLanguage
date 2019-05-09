package TypeChecker.Exceptions;

import SemanticAnalysis.Exceptions.BuildRecursionException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TypeInconsistencyExceptionTest {
    @Test
    void noMessage() {
        assertThrows(TypeInconsistencyException.class, () -> {
            throw new TypeInconsistencyException();
        });
    }

    @Test
    void message() {
        assertThrows(TypeInconsistencyException.class, () -> {
            throw new TypeInconsistencyException("message");
        });
    }

    @Test
    void throwable() {
        Throwable toThrow = new RuntimeException();
        assertThrows(TypeInconsistencyException.class, () -> {
            throw new TypeInconsistencyException(toThrow);
        });
    }
}