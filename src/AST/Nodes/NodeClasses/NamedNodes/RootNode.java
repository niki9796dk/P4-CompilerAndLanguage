package AST.Nodes.NodeClasses.NamedNodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNode;
import java_cup.runtime.ComplexSymbolFactory;

/**
 * A node that serves as the root of the AST.
 */
public class RootNode extends NamedNode {
    /**
     * Construct the root node.
     */
    public RootNode() {
        super("RootNode", NodeEnum.ROOT, new ComplexSymbolFactory.Location(0, 0));
    }
}
