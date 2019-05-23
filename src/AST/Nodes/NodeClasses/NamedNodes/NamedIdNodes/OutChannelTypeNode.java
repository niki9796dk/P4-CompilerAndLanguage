package AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;
import java_cup.runtime.ComplexSymbolFactory;

/**
 * An out channel node declared not in the block, but in a sub scope. Such as the blueprint.
 */
public class OutChannelTypeNode extends NamedIdNode {
    public OutChannelTypeNode(String id, ComplexSymbolFactory.Location location) {
        super("OutChannelType", id, NodeEnum.CHANNEL_OUT_TYPE, location);
    }
}
