package AST.Nodes.NodeClasses.NamedNodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNode;
import java_cup.runtime.ComplexSymbolFactory;

/**
 * A chain of channels specified by an arrow, example:
 * A -> B -> C
 */
public class ChainNode extends NamedNode {
    public ChainNode(ComplexSymbolFactory.Location location) {
        super("Chain", NodeEnum.CHAIN, location);
    }
}