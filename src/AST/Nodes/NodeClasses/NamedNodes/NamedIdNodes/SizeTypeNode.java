package AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;

/**
 * A reference connect a declared variable of the type "Size"
 */
public class SizeTypeNode extends NamedIdNode {
    public SizeTypeNode(String id) {
        super("SizeType", id, NodeEnum.SIZE_TYPE);
    }
}
