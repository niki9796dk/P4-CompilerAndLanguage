package AST.Nodes.NodeClasses.NamedNodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNode;

public class GroupNode extends NamedNode {
    public GroupNode() {
        super("Group", NodeEnum.GROUP);
    }

}