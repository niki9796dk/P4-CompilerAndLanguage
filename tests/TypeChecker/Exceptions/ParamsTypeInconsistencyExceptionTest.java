package TypeChecker.Exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParamsTypeInconsistencyExceptionTest {
    @Test
    void noMessage() {
        assertThrows(ParamsTypeInconsistencyException.class, () -> {
            throw new ParamsTypeInconsistencyException();
        });
    }

    @Test
    void message() {
        assertThrows(ParamsTypeInconsistencyException.class, () -> {
            throw new ParamsTypeInconsistencyException("message");
        });
    }

    @Test
    void throwable() {
        Throwable toThrow = new RuntimeException();
        assertThrows(ParamsTypeInconsistencyException.class, () -> {
            throw new ParamsTypeInconsistencyException(toThrow);
        });
    }
}