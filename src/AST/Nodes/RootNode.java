package AST.Nodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.NamedNode;

public class RootNode extends NamedNode {
    public RootNode() {
        super("RootNode", NodeEnum.ROOT);
    }
}
