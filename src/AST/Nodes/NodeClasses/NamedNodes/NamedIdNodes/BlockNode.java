package AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;

/**
 * A node representing a "block".
 */
public class BlockNode extends NamedIdNode {
    /**
     * @param id Create a block with the specified node
     */
    public BlockNode(String id) {
        super("Block", id, NodeEnum.BLOCK);
    }

    @Override
    public String toString() {
        return "\n\n" + super.toString();
    }
}