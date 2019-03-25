package AST.Nodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.NamedIdNode;

public class OutChannelNode extends NamedIdNode {
    public OutChannelNode(String id) {
        super("OutChannel", id, NodeEnum.CHANNEL_OUT);
    }
}
