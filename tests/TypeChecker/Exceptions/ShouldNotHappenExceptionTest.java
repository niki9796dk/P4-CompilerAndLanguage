package TypeChecker.Exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShouldNotHappenExceptionTest {
    @Test
    void noMessage() {
        assertThrows(ShouldNotHappenException.class, () -> {
            throw new ShouldNotHappenException();
        });
    }

    @Test
    void message() {
        assertThrows(ShouldNotHappenException.class, () -> {
            throw new ShouldNotHappenException("message");
        });
    }
}