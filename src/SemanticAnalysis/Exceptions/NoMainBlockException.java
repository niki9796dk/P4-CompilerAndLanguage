package SemanticAnalysis.Exceptions;

import SemanticAnalysis.Exceptions.SemanticProblemException;

/**
 * Runtime exception used for the compiler phase of semantic analysis.
 */
public class NoMainBlockException extends SemanticProblemException {

    /**
     * Default constructor with no message - Calls super
     */
    public NoMainBlockException() {
        super();
    }

    /**
     * Constructor for custom exception message - Calls super
     * @param message The exception message
     */
    public NoMainBlockException(String message) {
        super(message);
    }
}
