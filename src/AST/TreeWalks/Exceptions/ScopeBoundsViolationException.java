package AST.TreeWalks.Exceptions;

/**
 * An exception that is thrown if a method violates the scope pound, often if a scope returns as null.
 */
public class ScopeBoundsViolationException extends RuntimeException {

    /**
     * The default error message if none is given.
     */
    private static final String DEFAULT_ERROR_MESSAGE = "Illegal Scope Bound Violation Occurred.";

    /**
     * Prints the default error message in the stacktrace.
     */
    public ScopeBoundsViolationException() {
        super(DEFAULT_ERROR_MESSAGE);
    }

    /**
     * Writes an unique message to the error message of the stack trace.
     * @param message The new message.
     */
    public ScopeBoundsViolationException(String message) {
        super(message);
    }
}
