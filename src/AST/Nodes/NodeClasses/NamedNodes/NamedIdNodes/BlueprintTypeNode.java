package AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;
import java_cup.runtime.ComplexSymbolFactory;

/**
 * A node for a local variable within the blueprints scope.
 *
 * @see AST.Nodes.NodeClasses.NamedNodes.BlueprintNode
 */
public class BlueprintTypeNode extends NamedIdNode {
    public BlueprintTypeNode(String id, ComplexSymbolFactory.Location location) {
        super("BlueprintType", id, NodeEnum.BLUEPRINT_TYPE, location);
    }
}
