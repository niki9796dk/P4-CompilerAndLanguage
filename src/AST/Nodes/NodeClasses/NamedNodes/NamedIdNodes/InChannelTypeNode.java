package AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;
import java_cup.runtime.ComplexSymbolFactory;

/**
 * An in channel node declared not in the block, but in a sub scope. Such as the blueprint.
 */
public class InChannelTypeNode extends NamedIdNode {
    public InChannelTypeNode(String id, ComplexSymbolFactory.Location location) {
        super("InChannelType", id, NodeEnum.CHANNEL_IN_TYPE, location);
    }
}
