package AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;

/**
 * A node for a local variable within the blueprints scope.
 * Child of {@link BlockNode}
 * Parent of no other nodes.
 */
public class BlockTypeNode extends NamedIdNode {
    public BlockTypeNode(String id) {
        super("BlockType", id, NodeEnum.BLOCK_TYPE);
    }
}
