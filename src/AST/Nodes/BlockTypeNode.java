package AST.Nodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.NamedIdNode;

public class BlockTypeNode extends NamedIdNode {
    public BlockTypeNode(String id) {
        super("BlockType", id, NodeEnum.BLOCK_TYPE);
    }
}
