package ScopeChecker.Exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NoSuchVariableDeclaredExceptionTest {
    @Test
    void noMessage() {
        assertThrows(NoSuchVariableDeclaredException.class, () -> {
            throw new NoSuchVariableDeclaredException();
        });
    }

    @Test
    void message() {
        assertThrows(NoSuchVariableDeclaredException.class, () -> {
            throw new NoSuchVariableDeclaredException("message");
        });
    }
}