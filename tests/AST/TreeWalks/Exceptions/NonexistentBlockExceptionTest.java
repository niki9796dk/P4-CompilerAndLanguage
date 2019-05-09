package AST.TreeWalks.Exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NonexistentBlockExceptionTest {
    @Test
    void noMessage() {
        assertThrows(NonexistentBlockException.class, () -> {
            throw new NonexistentBlockException();
        });
    }

    @Test
    void message() {
        assertThrows(NonexistentBlockException.class, () -> {
            throw new NonexistentBlockException("message");
        });
    }
}