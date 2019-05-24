package CompilerExceptions.TypeExceptions;

import AST.Nodes.AbstractNodes.Nodes.AbstractNode;
import CompilerExceptions.CompilerException;

public class ShouldNotHappenException extends CompilerException {
    public ShouldNotHappenException(AbstractNode node) {
        super(node);
    }

    public ShouldNotHappenException(AbstractNode node, String message) {
        super(node, "SHOULT NOT HAPPEN HERE!! - " + message);
    }
}
