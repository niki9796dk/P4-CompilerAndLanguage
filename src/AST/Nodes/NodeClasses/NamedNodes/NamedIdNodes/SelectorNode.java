package AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;
import java_cup.runtime.ComplexSymbolFactory;

/**
 * Selects an instance of something channel an ID.
 * Child many different nodes. One of which is {@link BuildNode}.
 */
public class SelectorNode extends NamedIdNode {
    public SelectorNode(String id, ComplexSymbolFactory.Location location) {
        super("Selector", id, NodeEnum.SELECTOR, location);
    }
}
