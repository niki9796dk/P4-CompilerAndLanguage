package AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;

public class BlockTypeNode extends NamedIdNode {
    public BlockTypeNode(String id) {
        super("BlockType", id, NodeEnum.BLOCK_TYPE);
    }
}
