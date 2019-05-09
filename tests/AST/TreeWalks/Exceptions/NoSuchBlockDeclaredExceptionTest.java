package AST.TreeWalks.Exceptions;

import ScopeChecker.Exceptions.NoSuchBlockDeclaredException;
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
}