package AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;

/**
 * An out channel node declared not in the block, but in a sub scope. Such as the blueprint.
 */
public class OutChannelTypeNode extends NamedIdNode {
    public OutChannelTypeNode(String id) {
        super("OutChannelType", id, NodeEnum.CHANNEL_OUT_TYPE);
    }
}
