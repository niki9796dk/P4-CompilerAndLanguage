package CompilerExceptions.SemanticExceptions;

import AST.Nodes.AbstractNodes.Nodes.AbstractNode;

/**
 * Runtime exception used for the compiler phase of semantic analysis.
 */
public class ChainConnectionMismatchException extends SemanticProblemException {

    /**
     * Default constructor with no message - Calls super
     */
    public ChainConnectionMismatchException(AbstractNode node) {
        super(node);
    }

    /**
     * Constructor for custom exception message - Calls super
     * @param message The exception message
     */
    public ChainConnectionMismatchException(AbstractNode node, String message) {
        super(node, message);
    }
}
