package CompilerExceptions.SemanticExceptions;

import AST.Nodes.AbstractNodes.Nodes.AbstractNode;

/**
 * Runtime exception used for the compiler phase of semantic analysis.
 */
public class GroupConnectionMismatchException extends SemanticProblemException {

    /**
     * Default constructor with no message - Calls super
     */
    public GroupConnectionMismatchException(AbstractNode node) {
        super(node);
    }

    /**
     * Constructor for custom exception message - Calls super
     * @param message The exception message
     */
    public GroupConnectionMismatchException(AbstractNode node, String message) {
        super(node, message);
    }
}
