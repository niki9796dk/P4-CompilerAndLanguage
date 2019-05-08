package SemanticAnalysis.Exceptions;

/**
 * Runtime exception used for the compiler phase of semantic analysis.
 */
public class SemanticProblemException extends RuntimeException {

    /**
     * Default constructor with no message - Calls super
     */
    public SemanticProblemException() {
        super();
    }

    /**
     * Constructor for custom exception message - Calls super
     * @param message The exception message
     */
    public SemanticProblemException(String message) {
        super(message);
    }
}
