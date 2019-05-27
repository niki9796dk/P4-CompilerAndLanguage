package AST.Nodes.NodeClasses.NamedNodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNode;
import java_cup.runtime.ComplexSymbolFactory;

/**
 * A node representing the blueprint of a block.
 *
 * @see AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.BlockNode
 */
public class BlueprintNode extends NamedNode {
    public BlueprintNode(ComplexSymbolFactory.Location location) {
        super("Blueprint", NodeEnum.BLUEPRINT, location);
    }
}
