package SemanticAnalysis.Exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GroupConnectionMismatchExceptionTest {
    @Test
    void noMessage() {
        assertThrows(GroupConnectionMismatchException.class, () -> {
            throw new GroupConnectionMismatchException();
        });
    }

    @Test
    void message() {
        assertThrows(GroupConnectionMismatchException.class, () -> {
            throw new GroupConnectionMismatchException("message");
        });
    }
}