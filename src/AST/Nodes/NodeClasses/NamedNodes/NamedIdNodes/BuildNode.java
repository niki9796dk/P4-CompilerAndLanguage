package AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;

/**
 * Node used connect build a new instance object of a block class.
 * @see AST.Nodes.NodeClasses.NamedNodes.BlueprintNode
 * @see BlockNode
 * Parent of a {@link SelectorNode}.
 */
public class BuildNode extends NamedIdNode {
    public BuildNode(String id) {
        super("Build", id, NodeEnum.BUILD);
    }
}