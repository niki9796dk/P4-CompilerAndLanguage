package AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;

/**
 * Selects an instance of something using an ID.
 * Child many different nodes. One of which is {@link BuildNode}.
 */
public class SelectorNode extends NamedIdNode {
    public SelectorNode(String id) {
        super("Selector", id, NodeEnum.SELECTOR);
    }
}
