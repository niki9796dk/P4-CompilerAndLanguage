package AST.TreeWalks.Exceptions;

/**
 * An exception thrown when a non-declared block tries to be built or drawn.
 */
public class NonexistentBlockException extends ScopeBoundsViolationException{
    /**
     * The default error message if none is given.
     */
    private static final String DEFAULT_ERROR_MESSAGE = "Attempted Draw/Build of nonexistent block";

    /**
     * Prints the default error message in the stacktrace.
     */
    public NonexistentBlockException() {
        super(DEFAULT_ERROR_MESSAGE + ".");
    }

    /**
     * Appends a unique message to the error message of the stack trace.
     * @param message The appended message.
     */
    public NonexistentBlockException(String message) {
        super( DEFAULT_ERROR_MESSAGE + ": " + message);
    }
}
