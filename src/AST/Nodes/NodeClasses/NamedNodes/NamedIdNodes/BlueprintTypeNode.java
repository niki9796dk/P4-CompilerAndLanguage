package AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;

/**
 * A node for a local variable within the blueprints scope.
 * @see AST.Nodes.NodeClasses.NamedNodes.BlueprintNode
 */
public class BlueprintTypeNode extends NamedIdNode {
    public BlueprintTypeNode(String id) {
        super("BlueprintType", id, NodeEnum.BLUEPRINT_TYPE);
    }
}
