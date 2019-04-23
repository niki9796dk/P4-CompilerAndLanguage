package AST.Nodes.NodeClasses.NamedNodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNode;

public class RootNode extends NamedNode {
    public RootNode() {
        super("RootNode", NodeEnum.ROOT);
    }
}
