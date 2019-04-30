package AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;

/**
 * A node representing an input channel decleration of data flow in a block.
 * Represents nodes in the tree as well, but is an out channel when outside channel decelerations. [Citation Needed]
 * @see AST.Nodes.NodeClasses.NamedNodes.ChannelDeclarationsNode
 */
public class MyInChannelNode extends NamedIdNode {
    public MyInChannelNode(String id) {
        super("MyInChannel", id, NodeEnum.CHANNEL_IN_MY);
    }
}
