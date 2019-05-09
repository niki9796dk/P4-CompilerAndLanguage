package TypeChecker.Exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChannelPlacementTypeExceptionTest {
    @Test
    void noMessage() {
        assertThrows(ChannelPlacementTypeException.class, () -> {
            throw new ChannelPlacementTypeException();
        });
    }

    @Test
    void message() {
        assertThrows(ChannelPlacementTypeException.class, () -> {
            throw new ChannelPlacementTypeException("message");
        });
    }

    @Test
    void throwable() {
        Throwable toThrow = new RuntimeException();
        assertThrows(ChannelPlacementTypeException.class, () -> {
            throw new ChannelPlacementTypeException(toThrow);
        });
    }
}