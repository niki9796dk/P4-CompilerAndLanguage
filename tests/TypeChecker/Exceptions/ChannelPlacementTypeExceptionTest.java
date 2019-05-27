package TypeChecker.Exceptions;

import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.DummyNode;
import CompilerExceptions.TypeExceptions.ChannelPlacementTypeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ChannelPlacementTypeExceptionTest {
    @Test
    void noMessage() {
        assertThrows(ChannelPlacementTypeException.class, () -> {
            throw new ChannelPlacementTypeException(new DummyNode());
        });
    }

    @Test
    void message() {
        assertThrows(ChannelPlacementTypeException.class, () -> {
            throw new ChannelPlacementTypeException(new DummyNode(), "message");
        });
    }

    @Test
    void throwable() {
        Throwable toThrow = new RuntimeException();
        assertThrows(ChannelPlacementTypeException.class, () -> {
            throw new ChannelPlacementTypeException(new DummyNode(), toThrow);
        });
    }
}