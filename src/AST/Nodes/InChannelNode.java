package AST.Nodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.NamedIdNode;

public class InChannelNode extends NamedIdNode {
    public InChannelNode(String id) {
        super("InChannel", id, NodeEnum.CHANNEL_IN);
    }
}
