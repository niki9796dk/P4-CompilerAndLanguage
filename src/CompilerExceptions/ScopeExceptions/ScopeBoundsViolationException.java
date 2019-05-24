package CompilerExceptions.ScopeExceptions;

import AST.Nodes.AbstractNodes.Nodes.AbstractNode;
import CompilerExceptions.CompilerException;

/**
 * An exception that is thrown if a method violates the scope pound, often if a scope returns as null.
 */
public class ScopeBoundsViolationException extends CompilerException {

    /**
     * The default error message if none is given.
     */
    private static final String DEFAULT_ERROR_MESSAGE = "Illegal Scope Bound Violation Occurred.";

    /**
     * Prints the default error message in the stacktrace.
     */
    public ScopeBoundsViolationException(AbstractNode node) {
        super(node, DEFAULT_ERROR_MESSAGE);
    }

    /**
     * Writes an unique message connect the error message of the stack trace.
     * @param message The new message.
     */
    public ScopeBoundsViolationException(AbstractNode node, String message) {
        super(node, message);
    }

    public ScopeBoundsViolationException(AbstractNode node, Throwable cause) {
        super(node, cause);
    }
}
