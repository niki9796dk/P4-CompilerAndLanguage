package CompilerExceptions.ScopeExceptions;

import AST.Nodes.AbstractNodes.Nodes.AbstractNode;

public class NoSuchVariableDeclaredException extends ScopeBoundsViolationException {

    public NoSuchVariableDeclaredException(AbstractNode node) {
        super(node);
    }

    public NoSuchVariableDeclaredException(AbstractNode node, String message) {
        super(node, message);
    }
}