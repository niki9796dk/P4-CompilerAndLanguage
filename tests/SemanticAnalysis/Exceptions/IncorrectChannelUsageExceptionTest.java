package SemanticAnalysis.Exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IncorrectChannelUsageExceptionTest {
    @Test
    void noMessage() {
        assertThrows(IncorrectChannelUsageException.class, () -> {
            throw new IncorrectChannelUsageException();
        });
    }

    @Test
    void message() {
        assertThrows(IncorrectChannelUsageException.class, () -> {
            throw new IncorrectChannelUsageException("message");
        });
    }
}