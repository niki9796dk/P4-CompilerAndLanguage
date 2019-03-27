package AST.Nodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.NamedIdNode;

public class DrawNode extends NamedIdNode {
    public DrawNode(String id) {
        super("Draw", id, NodeEnum.DRAW);
    }
}
