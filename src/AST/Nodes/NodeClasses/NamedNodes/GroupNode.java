package AST.Nodes.NodeClasses.NamedNodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNode;
import java_cup.runtime.ComplexSymbolFactory;

/**
 * A group of nodes, which contains several
 * If there is a group, it is the first child of a chain.
 */
public class GroupNode extends NamedNode {
    public GroupNode(ComplexSymbolFactory.Location location) {
        super("Group", NodeEnum.GROUP, location);
    }

}