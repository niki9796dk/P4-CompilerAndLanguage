package CompilerExceptions;

import AST.Nodes.AbstractNodes.Nodes.AbstractNode;

public class CompilerException extends RuntimeException {
    private AbstractNode errorNode;

    public AbstractNode getErrorNode() {
        return errorNode;
    }

    public CompilerException(AbstractNode node) {
        super();
        this.errorNode = node;
    }

    public CompilerException(AbstractNode node, String message) {
        super(message);
        this.errorNode = node;
    }

    public CompilerException(AbstractNode node, String message, Throwable cause) {
        super(message, cause);
        this.errorNode = node;
    }

    public CompilerException(AbstractNode node, Throwable cause) {
        super(cause);
        this.errorNode = node;
    }
}
