package SemanticAnalysis.Exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChainConnectionMismatchExceptionTest {
    @Test
    void noMessage() {
        assertThrows(ChainConnectionMismatchException.class, () -> {
            throw new ChainConnectionMismatchException();
        });
    }

    @Test
    void message() {
        assertThrows(ChainConnectionMismatchException.class, () -> {
            throw new ChainConnectionMismatchException("message");
        });
    }
}