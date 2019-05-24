package CompilerExceptions.TypeExceptions;

import AST.Nodes.AbstractNodes.Nodes.AbstractNode;

public class ParamsSizeInconsistencyException extends TypeInconsistencyException {
    public ParamsSizeInconsistencyException(AbstractNode node) {
        super(node);
    }

    public ParamsSizeInconsistencyException(AbstractNode node, String message) {
        super(node, message);
    }

    public ParamsSizeInconsistencyException(AbstractNode node, Throwable cause) {
        super(node, cause);
    }
}
