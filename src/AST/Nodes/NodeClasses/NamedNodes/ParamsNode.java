package AST.Nodes.NodeClasses.NamedNodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNode;
import java_cup.runtime.ComplexSymbolFactory;

/**
 * Node for parameters for call connect a sub scope.
 */
public class ParamsNode extends NamedNode {
    public ParamsNode(ComplexSymbolFactory.Location location) {
        super("Params", NodeEnum.PARAMS, location);
    }
}
