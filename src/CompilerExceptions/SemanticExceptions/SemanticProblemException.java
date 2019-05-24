package CompilerExceptions.SemanticExceptions;

import AST.Nodes.AbstractNodes.Nodes.AbstractNode;
import CompilerExceptions.CompilerException;

/**
 * Runtime exception used for the compiler phase of semantic analysis.
 */
public class SemanticProblemException extends CompilerException {

    /**
     * Default constructor with no message - Calls super
     */
    public SemanticProblemException(AbstractNode node) {
        super(node);
    }

    /**
     * Constructor for custom exception message - Calls super
     * @param message The exception message
     */
    public SemanticProblemException(AbstractNode node, String message) {
        super(node, message);
    }
}
