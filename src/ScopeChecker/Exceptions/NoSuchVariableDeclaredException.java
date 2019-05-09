package ScopeChecker.Exceptions;

public class NoSuchVariableDeclaredException extends ScopeBoundsViolationException {

    public NoSuchVariableDeclaredException() {
        super();
    }

    public NoSuchVariableDeclaredException(String message) {
        super(message);
    }
}