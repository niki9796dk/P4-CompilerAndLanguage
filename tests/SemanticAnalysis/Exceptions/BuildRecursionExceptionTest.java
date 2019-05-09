package SemanticAnalysis.Exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BuildRecursionExceptionTest {
    @Test
    void noMessage() {
        assertThrows(BuildRecursionException.class, () -> {
            throw new BuildRecursionException();
        });
    }

    @Test
    void message() {
        assertThrows(BuildRecursionException.class, () -> {
            throw new BuildRecursionException("message");
        });
    }
}