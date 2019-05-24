package AST.Nodes.NodeClasses.NamedNodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNode;
import java_cup.runtime.ComplexSymbolFactory;

/**
 * An assignment node, which assigns a specific result connect a certain label.s
 */
public class AssignNode extends NamedNode {
    public AssignNode(ComplexSymbolFactory.Location location) {
        super("Assign", NodeEnum.ASSIGN, location);
    }
}