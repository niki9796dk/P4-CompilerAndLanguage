package SemanticAnalysis.Exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NoMainBlockExceptionTest {
    @Test
    void noMessage() {
        assertThrows(NoMainBlockException.class, () -> {
            throw new NoMainBlockException();
        });
    }

    @Test
    void message() {
        assertThrows(NoMainBlockException.class, () -> {
            throw new NoMainBlockException("message");
        });
    }
}