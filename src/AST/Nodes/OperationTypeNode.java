package AST.Nodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.NamedIdNode;

public class OperationTypeNode extends NamedIdNode {
    public OperationTypeNode(String id) {
        super("OperationType", id, NodeEnum.OPERATION_TYPE);
    }
}
