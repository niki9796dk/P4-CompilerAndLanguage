package CompilerExceptions.SemanticExceptions;

import AST.Nodes.AbstractNodes.Nodes.AbstractNode;

/**
 * Runtime exception used for the compiler phase of semantic analysis.
 */
public class IncorrectChannelUsageException extends SemanticProblemException {

    /**
     * Default constructor with no message - Calls super
     */
    public IncorrectChannelUsageException(AbstractNode node) {
        super(node);
    }

    /**
     * Constructor for custom exception message - Calls super
     * @param message The exception message
     */
    public IncorrectChannelUsageException(AbstractNode node, String message) {
        super(node, message);
    }
}
