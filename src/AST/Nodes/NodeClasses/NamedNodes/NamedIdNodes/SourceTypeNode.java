package AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;
import java_cup.runtime.ComplexSymbolFactory;

/**
 * A source node, which only contains an output with no input.
 */
public class SourceTypeNode extends NamedIdNode {
    public SourceTypeNode(String id, ComplexSymbolFactory.Location location) {
        super("SourceType", id, NodeEnum.SOURCE_TYPE, location);
    }
}
