package AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;
import java_cup.runtime.ComplexSymbolFactory;

/**
 * Node used connect build a new instance object of a block class.
 * @see AST.Nodes.NodeClasses.NamedNodes.BlueprintNode
 * @see BlockNode
 * Parent of a {@link SelectorNode}.
 */
public class BuildNode extends NamedIdNode {
    public BuildNode(String id, ComplexSymbolFactory.Location location) {
        super("Build", id, NodeEnum.BUILD, location);
    }
}