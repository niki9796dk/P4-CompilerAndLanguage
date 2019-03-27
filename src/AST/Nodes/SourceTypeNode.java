package AST.Nodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.NamedIdNode;

public class SourceTypeNode extends NamedIdNode {
    public SourceTypeNode(String id) {
        super("SourceType", id, NodeEnum.SOURCE_TYPE);
    }
}
