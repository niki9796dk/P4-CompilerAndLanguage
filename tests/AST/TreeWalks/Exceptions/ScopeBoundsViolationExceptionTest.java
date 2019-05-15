package AST.TreeWalks.Exceptions;

import ScopeChecker.Exceptions.ScopeBoundsViolationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScopeBoundsViolationExceptionTest {

    @Test
    void noMessage() {
        assertThrows(ScopeBoundsViolationException.class, () -> {
            throw new ScopeBoundsViolationException();
        });
    }

    @Test
    void message() {
        assertThrows(ScopeBoundsViolationException.class, () -> {
            throw new ScopeBoundsViolationException("message");
        });
    }

    @Test
    void throwable() {
        assertThrows(RuntimeException.class, () -> {
            throw new ScopeBoundsViolationException(new RuntimeException());
        });
    }
}