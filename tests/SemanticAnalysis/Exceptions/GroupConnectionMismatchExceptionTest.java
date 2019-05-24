package SemanticAnalysis.Exceptions;

import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.DummyNode;
import CompilerExceptions.SemanticExceptions.GroupConnectionMismatchException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GroupConnectionMismatchExceptionTest {
    @Test
    void noMessage() {
        assertThrows(GroupConnectionMismatchException.class, () -> {
            throw new GroupConnectionMismatchException(new DummyNode());
        });
    }

    @Test
    void message() {
        assertThrows(GroupConnectionMismatchException.class, () -> {
            throw new GroupConnectionMismatchException(new DummyNode(), "message");
        });
    }
}