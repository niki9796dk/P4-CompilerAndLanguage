package AST.Nodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.NamedNode;

public class AssignNode extends NamedNode {
    public AssignNode() {
        super("Assign", NodeEnum.ASSIGN);
    }
}