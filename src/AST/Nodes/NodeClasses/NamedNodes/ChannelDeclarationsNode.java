package AST.Nodes.NodeClasses.NamedNodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNode;
import java_cup.runtime.ComplexSymbolFactory;

/**
 * The node containing the channel declarations.
 * More specifically, it contains {@link AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.MyInChannelNode} and {@link AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.MyOutChannelNode} objects.
 *
 * @see AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.BlockNode
 */
public class ChannelDeclarationsNode extends NamedNode {
    public ChannelDeclarationsNode(ComplexSymbolFactory.Location location) {
        super("ChannelDeclarations", NodeEnum.CHANNEL_DECLARATIONS, location);
    }
}