package AST.Nodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.NamedNode;

public class GroupNode extends NamedNode {
    public GroupNode() {
        super("Group", NodeEnum.GROUP);
    }

}