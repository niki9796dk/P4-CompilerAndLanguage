package ScopeChecker.Exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NoSuchChannelDeclaredExceptionTest {
    @Test
    void noMessage() {
        assertThrows(NoSuchChannelDeclaredException.class, () -> {
            throw new NoSuchChannelDeclaredException();
        });
    }

    @Test
    void message() {
        assertThrows(NoSuchChannelDeclaredException.class, () -> {
            throw new NoSuchChannelDeclaredException("message");
        });
    }
}