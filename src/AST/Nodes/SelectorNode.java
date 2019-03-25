package AST.Nodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.NamedIdNode;

public class SelectorNode extends NamedIdNode {
    public SelectorNode(String id) {
        super("Selector", id, NodeEnum.SELECTOR);
    }
}
