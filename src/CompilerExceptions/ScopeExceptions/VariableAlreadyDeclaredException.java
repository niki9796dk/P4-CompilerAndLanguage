package CompilerExceptions.ScopeExceptions;

import AST.Nodes.AbstractNodes.Nodes.AbstractNode;

public class VariableAlreadyDeclaredException extends ScopeBoundsViolationException  {
    public VariableAlreadyDeclaredException(AbstractNode node) {
        super(node);
    }

    public VariableAlreadyDeclaredException(AbstractNode node, String message) {
        super(node, message);
    }
}
