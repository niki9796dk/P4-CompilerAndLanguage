package SemanticAnalysis.Exceptions;

import SemanticAnalysis.Exceptions.SemanticProblemException;

/**
 * Runtime exception used for the compiler phase of semantic analysis.
 */
public class ChainConnectionMismatchException extends SemanticProblemException {

    /**
     * Default constructor with no message - Calls super
     */
    public ChainConnectionMismatchException() {
        super();
    }

    /**
     * Constructor for custom exception message - Calls super
     * @param message The exception message
     */
    public ChainConnectionMismatchException(String message) {
        super(message);
    }
}
