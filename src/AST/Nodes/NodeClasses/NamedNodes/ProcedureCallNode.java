package AST.Nodes.NodeClasses.NamedNodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNode;

/**
 * A node representing a call to a specific procedure
 * @see ParamsNode
 */
public class ProcedureCallNode extends NamedNode {
    public ProcedureCallNode() {
        super("ProcedureCall", NodeEnum.PROCEDURE_CALL);
    }
}
