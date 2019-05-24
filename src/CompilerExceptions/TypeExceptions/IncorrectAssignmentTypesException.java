package CompilerExceptions.TypeExceptions;

import AST.Nodes.AbstractNodes.Nodes.AbstractNode;

public class IncorrectAssignmentTypesException extends TypeInconsistencyException {
    public IncorrectAssignmentTypesException(AbstractNode node) {
        super(node);
    }

    public IncorrectAssignmentTypesException(AbstractNode node, String message) {
        super(node, message);
    }

    public IncorrectAssignmentTypesException(AbstractNode node, Throwable cause) {
        super(node, cause);
    }
}
