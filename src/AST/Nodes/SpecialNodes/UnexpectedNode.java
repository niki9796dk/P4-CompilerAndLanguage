package AST.Nodes.SpecialNodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNode;
import java_cup.runtime.ComplexSymbolFactory;

/**
 * A node used as a filler node during development. Should not be deployed in any final releases.
 */
public class UnexpectedNode extends NamedNode {
    /**
     * Constructs a temporary node without any enum.
     * @param name  The unique name of the node.
     */
    public UnexpectedNode(String name) {
        super(name, NodeEnum.NONEXISTENT, new ComplexSymbolFactory.Location(-1, -1));
    }
}