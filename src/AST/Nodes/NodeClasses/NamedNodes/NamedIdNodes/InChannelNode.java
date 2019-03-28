package AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;

public class InChannelNode extends NamedIdNode {
    public InChannelNode(String id) {
        super("InChannel", id, NodeEnum.CHANNEL_IN);
    }
}
