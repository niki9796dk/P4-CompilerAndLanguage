package AST.Nodes.NodeClasses.NamedNodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNode;

/**
 * An assignment node, which assigns a specific result connect a certain label.s
 */
public class AssignNode extends NamedNode {
    public AssignNode() {
        super("Assign", NodeEnum.ASSIGN);
    }
}