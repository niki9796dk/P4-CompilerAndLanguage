package SemanticAnalysis.Exceptions;

import SemanticAnalysis.Exceptions.SemanticProblemException;

/**
 * Runtime exception used for the compiler phase of semantic analysis.
 */
public class GroupConnectionMismatchException extends SemanticProblemException {

    /**
     * Default constructor with no message - Calls super
     */
    public GroupConnectionMismatchException() {
        super();
    }

    /**
     * Constructor for custom exception message - Calls super
     * @param message The exception message
     */
    public GroupConnectionMismatchException(String message) {
        super(message);
    }
}
