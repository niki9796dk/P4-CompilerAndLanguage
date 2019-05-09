package TypeChecker.Exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IncorrectAssignmentTypesExceptionTest {
    @Test
    void noMessage() {
        assertThrows(IncorrectAssignmentTypesException.class, () -> {
            throw new IncorrectAssignmentTypesException();
        });
    }

    @Test
    void message() {
        assertThrows(IncorrectAssignmentTypesException.class, () -> {
            throw new IncorrectAssignmentTypesException("message");
        });
    }

    @Test
    void throwable() {
        Throwable toThrow = new RuntimeException();
        assertThrows(IncorrectAssignmentTypesException.class, () -> {
            throw new IncorrectAssignmentTypesException(toThrow);
        });
    }
}