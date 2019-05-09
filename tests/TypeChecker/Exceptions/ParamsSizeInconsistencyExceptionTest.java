package TypeChecker.Exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParamsSizeInconsistencyExceptionTest {
    @Test
    void noMessage() {
        assertThrows(ParamsSizeInconsistencyException.class, () -> {
            throw new ParamsSizeInconsistencyException();
        });
    }

    @Test
    void message() {
        assertThrows(ParamsSizeInconsistencyException.class, () -> {
            throw new ParamsSizeInconsistencyException("message");
        });
    }

    @Test
    void throwable() {
        Throwable toThrow = new RuntimeException();
        assertThrows(ParamsSizeInconsistencyException.class, () -> {
            throw new ParamsSizeInconsistencyException(toThrow);
        });
    }
}