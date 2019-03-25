package AST.Nodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.NamedIdNode;
import AST.Nodes.AbstractNodes.NamedNode;

public class ChannelDeclarationsNode extends NamedNode {
    public ChannelDeclarationsNode() {
        super("ChannelDeclarations", NodeEnum.CHANNEL_DECLARATIONS);
    }
}