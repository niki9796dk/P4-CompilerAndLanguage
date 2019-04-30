package AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;

/**
 * A source node, which only contains an output with no input.
 */
public class SourceTypeNode extends NamedIdNode {
    public SourceTypeNode(String id) {
        super("SourceType", id, NodeEnum.SOURCE_TYPE);
    }
}
