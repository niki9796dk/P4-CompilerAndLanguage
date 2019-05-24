package CompilerExceptions.TypeExceptions;

import AST.Nodes.AbstractNodes.Nodes.AbstractNode;
import CompilerExceptions.CompilerException;

public class TypeInconsistencyException extends CompilerException {
    public TypeInconsistencyException(AbstractNode node) {
        super(node);
    }

    public TypeInconsistencyException(AbstractNode node, String message) {
        super(node, message);
    }

    public TypeInconsistencyException(AbstractNode node, Throwable cause) {
        super(node, cause);
    }
}
