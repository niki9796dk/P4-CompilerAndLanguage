package AST.Nodes.NodeClasses.NamedNodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNode;

public class ChannelDeclarationsNode extends NamedNode {
    public ChannelDeclarationsNode() {
        super("ChannelDeclarations", NodeEnum.CHANNEL_DECLARATIONS);
    }
}