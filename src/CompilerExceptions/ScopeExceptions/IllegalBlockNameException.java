package CompilerExceptions.ScopeExceptions;

import AST.Nodes.AbstractNodes.Nodes.AbstractNode;

public class IllegalBlockNameException extends ScopeBoundsViolationException {

    public IllegalBlockNameException(AbstractNode node) {
        super(node);
    }

    public IllegalBlockNameException(AbstractNode node, String message) {
        super(node, message);
    }

    public IllegalBlockNameException(AbstractNode node, Throwable cause) {
        super(node, cause);
    }
}
