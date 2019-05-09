package SemanticAnalysis.Exceptions;

/**
 * Runtime exception used for the compiler phase of semantic analysis.
 */
public class IncorrectChannelUsageException extends SemanticProblemException {

    /**
     * Default constructor with no message - Calls super
     */
    public IncorrectChannelUsageException() {
        super();
    }

    /**
     * Constructor for custom exception message - Calls super
     * @param message The exception message
     */
    public IncorrectChannelUsageException(String message) {
        super(message);
    }
}
