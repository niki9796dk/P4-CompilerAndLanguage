package AST.TreeWalks.Exceptions;

import ScopeChecker.Exceptions.NoSuchBlockDeclaredException;
import ScopeChecker.Exceptions.ScopeBoundsViolationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NoSuchBlockDeclaredExceptionTest {
    @Test
    void noMessage() {
        assertThrows(NoSuchBlockDeclaredException.class, () -> {
            throw new NoSuchBlockDeclaredException();
        });
    }

    @Test
    void message() {
        assertThrows(NoSuchBlockDeclaredException.class, () -> {
            throw new NoSuchBlockDeclaredException("message");
        });
    }

    @Test
    void throwable() {
        assertThrows(RuntimeException.class, () -> {
            throw new NoSuchBlockDeclaredException(new RuntimeException());
        });
    }
}