package AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;

/**
 * A node representing an output channel connect flow data through.
 * Represents nodes in the tree as well, but is an in channel when outside channel decelerations.
 * @see AST.Nodes.NodeClasses.NamedNodes.ChannelDeclarationsNode
 */
public class MyOutChannelNode extends NamedIdNode {
    public MyOutChannelNode(String id) {
        super("MyOutChannel", id, NodeEnum.CHANNEL_OUT_MY);
    }
}
