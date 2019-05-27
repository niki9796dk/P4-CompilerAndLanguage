package CompilerExceptions.SemanticExceptions;

import AST.Nodes.AbstractNodes.Nodes.AbstractNode;

/**
 * Runtime exception used for the compiler phase of semantic analysis.
 */
public class NoMainBlockException extends SemanticProblemException {

    /**
     * Default constructor with no message - Calls super
     */
    public NoMainBlockException(AbstractNode node) {
        super(node);
    }

    /**
     * Constructor for custom exception message - Calls super
     *
     * @param message The exception message
     */
    public NoMainBlockException(AbstractNode node, String message) {
        super(node, message);
    }
}
