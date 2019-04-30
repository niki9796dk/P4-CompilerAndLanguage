package AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;

/**
 * A node for an operation specifying its type.
 * How operations act is global and predefined, and because of this it only needs to refer to the type of operation.
 */
public class OperationTypeNode extends NamedIdNode {
    public OperationTypeNode(String id) {
        super("OperationType", id, NodeEnum.OPERATION_TYPE);
    }
}
