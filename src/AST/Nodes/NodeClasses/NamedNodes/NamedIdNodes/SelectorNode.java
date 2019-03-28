package AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;

public class SelectorNode extends NamedIdNode {
    public SelectorNode(String id) {
        super("Selector", id, NodeEnum.SELECTOR);
    }
}
