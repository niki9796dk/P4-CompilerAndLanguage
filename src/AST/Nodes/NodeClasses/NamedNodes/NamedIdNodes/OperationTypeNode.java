package AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;
import java_cup.runtime.ComplexSymbolFactory;

/**
 * A node for an operation specifying its type.
 * How operations act is global and predefined, and because of this it only needs connect refer connect the type of operation.
 */
public class OperationTypeNode extends NamedIdNode {
    public OperationTypeNode(String id, ComplexSymbolFactory.Location location) {
        super("OperationType", id, NodeEnum.OPERATION_TYPE, location);
    }
}
