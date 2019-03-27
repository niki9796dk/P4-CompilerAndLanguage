package AST.Nodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.NamedIdNode;

public class BuildNode extends NamedIdNode {
    public BuildNode(String id) {
        super("Build", id, NodeEnum.BUILD);
    }
}