package ScopeChecker.Exceptions;

import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.DummyNode;
import CompilerExceptions.ScopeExceptions.VariableAlreadyDeclaredException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class VariableAlreadyDeclaredExceptionTest {
    @Test
    void noMessage() {
        assertThrows(VariableAlreadyDeclaredException.class, () -> {
            throw new VariableAlreadyDeclaredException(new DummyNode());
        });
    }

    @Test
    void message() {
        assertThrows(VariableAlreadyDeclaredException.class, () -> {
            throw new VariableAlreadyDeclaredException(new DummyNode(), "message");
        });
    }
}