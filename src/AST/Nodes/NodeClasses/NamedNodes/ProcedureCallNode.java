package AST.Nodes.NodeClasses.NamedNodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNode;

public class ProcedureCallNode extends NamedNode {
    public ProcedureCallNode() {
        super("ProcedureCall", NodeEnum.PROCEDURE_CALL);
    }
}
