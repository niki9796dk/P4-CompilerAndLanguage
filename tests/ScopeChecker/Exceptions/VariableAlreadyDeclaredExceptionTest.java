package ScopeChecker.Exceptions;

import SemanticAnalysis.Exceptions.BuildRecursionException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VariableAlreadyDeclaredExceptionTest {
    @Test
    void noMessage() {
        assertThrows(VariableAlreadyDeclaredException.class, () -> {
            throw new VariableAlreadyDeclaredException();
        });
    }

    @Test
    void message() {
        assertThrows(VariableAlreadyDeclaredException.class, () -> {
            throw new VariableAlreadyDeclaredException("message");
        });
    }
}