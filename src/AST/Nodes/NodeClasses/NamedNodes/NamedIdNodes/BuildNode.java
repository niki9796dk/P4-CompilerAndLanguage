package AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;

public class BuildNode extends NamedIdNode {
    public BuildNode(String id) {
        super("Build", id, NodeEnum.BUILD);
    }
}