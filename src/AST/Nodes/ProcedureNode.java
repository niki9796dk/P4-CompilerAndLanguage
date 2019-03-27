package AST.Nodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.NamedIdNode;

public class ProcedureNode extends NamedIdNode {
    public ProcedureNode(String id) {
        super("Procedure", id, NodeEnum.PROCEDURE);
    }
}