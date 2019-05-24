package CompilerExceptions.TypeExceptions;

import AST.Nodes.AbstractNodes.Nodes.AbstractNode;

public class ChannelPlacementTypeException extends TypeInconsistencyException {
    public ChannelPlacementTypeException(AbstractNode node) {
        super(node);
    }

    public ChannelPlacementTypeException(AbstractNode node, String message) {
        super(node, message);
    }

    public ChannelPlacementTypeException(AbstractNode node, Throwable cause) {
        super(node, cause);
    }
}
