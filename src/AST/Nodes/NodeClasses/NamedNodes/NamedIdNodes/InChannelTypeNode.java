package AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;

public class InChannelTypeNode extends NamedIdNode {
    public InChannelTypeNode(String id) {
        super("InChannelType", id, NodeEnum.CHANNEL_IN_TYPE);
    }
}
