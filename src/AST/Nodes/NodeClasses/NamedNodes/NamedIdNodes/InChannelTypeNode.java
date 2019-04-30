package AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;

/**
 * An in channel node declared not in the block, but in a sub scope. Such as the blueprint.
 */
public class InChannelTypeNode extends NamedIdNode {
    public InChannelTypeNode(String id) {
        super("InChannelType", id, NodeEnum.CHANNEL_IN_TYPE);
    }
}
