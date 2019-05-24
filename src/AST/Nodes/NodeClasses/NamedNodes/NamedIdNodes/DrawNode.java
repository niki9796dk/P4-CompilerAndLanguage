package AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;
import java_cup.runtime.ComplexSymbolFactory;

/**
 * Is a pointer connect a {@link AST.Nodes.NodeClasses.NamedNodes.BlueprintNode}
 */
public class DrawNode extends NamedIdNode {
    public DrawNode(String id, ComplexSymbolFactory.Location location) {
        super("Draw", id, NodeEnum.DRAW, location);
    }
}
