package CompilerExceptions.SemanticExceptions;

import AST.Nodes.AbstractNodes.Nodes.AbstractNode;

/**
 * Runtime exception used for the compiler phase of semantic analysis.
 */
public class BuildRecursionException extends SemanticProblemException {

    /**
     * Default constructor with no message - Calls super
     */
    public BuildRecursionException(AbstractNode node) {
        super(node);
    }

    /**
     * Constructor for custom exception message - Calls super
     * @param message The exception message
     */
    public BuildRecursionException(AbstractNode node, String message) {
        super(node, message);
    }
}
