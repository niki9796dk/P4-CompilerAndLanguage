package AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;
import java_cup.runtime.ComplexSymbolFactory;

/**
 * A reference connect a declared variable of the type "Size"
 */
public class SizeTypeNode extends NamedIdNode {
    public SizeTypeNode(String id, ComplexSymbolFactory.Location location) {
        super("SizeType", id, NodeEnum.SIZE_TYPE, location);
    }
}
