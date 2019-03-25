package AST.Nodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.NamedIdNode;

public class BlueprintTypeNode extends NamedIdNode {
    public BlueprintTypeNode(String id) {
        super("BlueprintType", id, NodeEnum.BLUEPRINT_TYPE);
    }
}
