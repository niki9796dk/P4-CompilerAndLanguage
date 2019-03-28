package AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;

public class OperationTypeNode extends NamedIdNode {
    public OperationTypeNode(String id) {
        super("OperationType", id, NodeEnum.OPERATION_TYPE);
    }
}
