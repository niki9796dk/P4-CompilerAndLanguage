package AST.Nodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.NamedIdNode;

public class BlockNode extends NamedIdNode {
    public BlockNode(String id) {
        super("Block", id, NodeEnum.BLOCK);
    }

    @Override
    public String toString() {
        return "\n\n" + super.toString();
    }
}