package AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;
import java_cup.runtime.ComplexSymbolFactory;

/**
 * A node for a local variable within the blueprints scope.
 * Child of {@link BlockNode}
 * Parent of no other nodes.
 */
public class BlockTypeNode extends NamedIdNode {
    public BlockTypeNode(String id, ComplexSymbolFactory.Location location) {
        super("BlockType", id, NodeEnum.BLOCK_TYPE, location);
    }
}
