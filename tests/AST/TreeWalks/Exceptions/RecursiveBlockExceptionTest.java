package AST.TreeWalks.Exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RecursiveBlockExceptionTest {
    @Test
    void noMessage() {
        assertThrows(RecursiveBlockException.class, () -> {
            throw new RecursiveBlockException();
        });
    }

    @Test
    void message() {
        assertThrows(RecursiveBlockException.class, () -> {
            throw new RecursiveBlockException("message");
        });
    }
}