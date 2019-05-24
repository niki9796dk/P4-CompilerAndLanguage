package CompilerExceptions.ScopeExceptions;

import AST.Nodes.AbstractNodes.Nodes.AbstractNode;

public class IllegalProcedureCallScopeException extends ScopeBoundsViolationException {

    public IllegalProcedureCallScopeException(AbstractNode node) {
        super(node);
    }

    public IllegalProcedureCallScopeException(AbstractNode node, String message) {
        super(node, message);
    }

    public IllegalProcedureCallScopeException(AbstractNode node, Throwable cause) {
        super(node, cause);
    }
}
