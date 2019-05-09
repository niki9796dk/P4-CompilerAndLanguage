package AST.TreeWalks.Exceptions;

import AST.Nodes.AbstractNodes.NodeEnumAble;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.BlockNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScopeBoundsViolationExceptionTest {

    @Test
    void noMessage() {
        assertThrows(ScopeBoundsViolationException.class, () -> {
            throw new ScopeBoundsViolationException();
        });
    }

    @Test
    void message() {
        assertThrows(ScopeBoundsViolationException.class, () -> {
            throw new ScopeBoundsViolationException("message");
        });
    }
}