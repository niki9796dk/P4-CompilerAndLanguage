package ScopeChecker.Exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IllegalProcedureCallScopeExceptionTest {
    @Test
    void noMessage() {
        assertThrows(IllegalProcedureCallScopeException.class, () -> {
            throw new IllegalProcedureCallScopeException();
        });
    }

    @Test
    void message() {
        assertThrows(IllegalProcedureCallScopeException.class, () -> {
            throw new IllegalProcedureCallScopeException("message");
        });
    }

    @Test
    void throwable() {
        assertThrows(RuntimeException.class, () -> {
            throw new IllegalProcedureCallScopeException(new RuntimeException());
        });
    }
}