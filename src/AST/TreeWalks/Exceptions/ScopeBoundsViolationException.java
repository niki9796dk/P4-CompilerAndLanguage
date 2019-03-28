package AST.TreeWalks.Exceptions;

public class ScopeBoundsViolationException extends RuntimeException {

    private static final String DEFAULT_ERROR_MESSAGE = "Illegal Scope Bound Violation Occurred.";

    public ScopeBoundsViolationException() {
        super(DEFAULT_ERROR_MESSAGE);
    }

    public ScopeBoundsViolationException(String message) {
        super(message);
    }
}
