package AST.Nodes.NodeClasses.NamedNodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNode;

/**
 * A node representing the blueprint of a block.
 * @see AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.BlockNode
 */
public class BlueprintNode extends NamedNode {
    public BlueprintNode() {
        super("Blueprint", NodeEnum.BLUEPRINT);
    }
}
