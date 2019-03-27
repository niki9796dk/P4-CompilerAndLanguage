package AST.Nodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.NamedIdNode;

public class SizeTypeNode extends NamedIdNode {
    public SizeTypeNode(String id) {
        super("SizeType", id, NodeEnum.SIZE_TYPE);
    }
}
