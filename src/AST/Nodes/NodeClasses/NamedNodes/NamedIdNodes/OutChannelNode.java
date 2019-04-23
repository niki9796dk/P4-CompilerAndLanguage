package AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;

public class OutChannelNode extends NamedIdNode {
    public OutChannelNode(String id) {
        super("OutChannel", id, NodeEnum.CHANNEL_OUT);
    }
}
