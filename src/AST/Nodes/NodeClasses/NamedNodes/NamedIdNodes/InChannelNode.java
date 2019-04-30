package AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;

/**
 * A node representing an input channel of data flow.
 * Represents nodes in the tree as well, but is an out channel when outside channel decelerations.
 * @see AST.Nodes.NodeClasses.NamedNodes.ChannelDeclarationsNode
 */
public class InChannelNode extends NamedIdNode {
    public InChannelNode(String id) {
        super("InChannel", id, NodeEnum.CHANNEL_IN);
    }
}
