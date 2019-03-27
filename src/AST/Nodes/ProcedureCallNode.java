package AST.Nodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.NamedNode;

public class ProcedureCallNode extends NamedNode {
    public ProcedureCallNode() {
        super("ProcedureCall", NodeEnum.PROCEDURE_CALL);
    }
}
