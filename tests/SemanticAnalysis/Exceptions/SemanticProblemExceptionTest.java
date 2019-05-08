package SemanticAnalysis.Exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SemanticProblemExceptionTest {

    @Test
    void noMessage() {
        assertThrows(SemanticProblemException.class, () -> {
            throw new SemanticProblemException();
        });
    }

    @Test
    void message() {
        assertThrows(SemanticProblemException.class, () -> {
            throw new SemanticProblemException("message");
        });
    }
}