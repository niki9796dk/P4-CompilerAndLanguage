package CompilerExceptions.TypeExceptions;

import AST.Nodes.AbstractNodes.Nodes.AbstractNode;

public class ParamsTypeInconsistencyException extends TypeInconsistencyException {
    public ParamsTypeInconsistencyException(AbstractNode node) {
        super(node);
    }

    public ParamsTypeInconsistencyException(AbstractNode node, String message) {
        super(node, message);
    }

    public ParamsTypeInconsistencyException(AbstractNode node, Throwable cause) {
        super(node, cause);
    }
}
