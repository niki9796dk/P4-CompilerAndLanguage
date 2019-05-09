package ScopeChecker.Exceptions;

public class IllegalProcedureCallScopeException extends ScopeBoundsViolationException {

    public IllegalProcedureCallScopeException() {
        super();
    }

    public IllegalProcedureCallScopeException(String message) {
        super(message);
    }
}
