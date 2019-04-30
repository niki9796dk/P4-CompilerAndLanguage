package AST.Nodes.NodeClasses.NamedNodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNode;

/**
 * A chain of channels specified by an arrow, example:
 * A -> B -> C
 */
public class ChainNode extends NamedNode {
    public ChainNode() {
        super("Chain", NodeEnum.CHAIN);
    }
}