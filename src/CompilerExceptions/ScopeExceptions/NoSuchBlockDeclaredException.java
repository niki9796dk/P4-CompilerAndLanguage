package CompilerExceptions.ScopeExceptions;

import AST.Nodes.AbstractNodes.Nodes.AbstractNode;

/**
 * An exception thrown when a non-declared block tries to be built or drawn.
 */
public class NoSuchBlockDeclaredException extends ScopeBoundsViolationException {
    /**
     * The default error message if none is given.
     */
    private static final String DEFAULT_ERROR_MESSAGE = "Attempted Draw/Build of nonexistent block";

    /**
     * Prints the default error message in the stacktrace.
     */
    public NoSuchBlockDeclaredException(AbstractNode node) {
        super(node, DEFAULT_ERROR_MESSAGE + ".");
    }

    /**
     * Appends a unique message to the error message of the stack trace.
     * @param message The appended message.
     */
    public NoSuchBlockDeclaredException(AbstractNode node, String message) {
        super(node, DEFAULT_ERROR_MESSAGE + ": " + message);
    }

    public NoSuchBlockDeclaredException(AbstractNode node, Throwable cause) {
        super(node, cause);
    }
}
