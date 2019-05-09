package SemanticAnalysis.Exceptions;

/**
 * Runtime exception used for the compiler phase of semantic analysis.
 */
public class BuildRecursionException extends SemanticProblemException {

    /**
     * Default constructor with no message - Calls super
     */
    public BuildRecursionException() {
        super();
    }

    /**
     * Constructor for custom exception message - Calls super
     * @param message The exception message
     */
    public BuildRecursionException(String message) {
        super(message);
    }
}
