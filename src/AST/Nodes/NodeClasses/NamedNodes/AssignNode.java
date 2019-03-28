package AST.Nodes.NodeClasses.NamedNodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNode;

public class AssignNode extends NamedNode {
    public AssignNode() {
        super("Assign", NodeEnum.ASSIGN);
    }
}