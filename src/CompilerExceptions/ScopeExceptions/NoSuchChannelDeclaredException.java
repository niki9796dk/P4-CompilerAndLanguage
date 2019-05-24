package CompilerExceptions.ScopeExceptions;

import AST.Nodes.AbstractNodes.Nodes.AbstractNode;

public class NoSuchChannelDeclaredException extends ScopeBoundsViolationException {

    public NoSuchChannelDeclaredException(AbstractNode node) {
        super(node);
    }

    public NoSuchChannelDeclaredException(AbstractNode node, String message) {
        super(node, message);
    }
}