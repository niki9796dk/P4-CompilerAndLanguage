package AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;

public class ProcedureNode extends NamedIdNode {
    public ProcedureNode(String id) {
        super("Procedure", id, NodeEnum.PROCEDURE);
    }
}