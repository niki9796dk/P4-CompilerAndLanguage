package AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;

public class OutChannelTypeNode extends NamedIdNode {
    public OutChannelTypeNode(String id) {
        super("OutChannelType", id, NodeEnum.CHANNEL_OUT_TYPE);
    }
}
