package AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;
import java_cup.runtime.ComplexSymbolFactory;

/**
 * A procedure within a {@link BlockNode}
 */
public class ProcedureNode extends NamedIdNode {
    public ProcedureNode(String id, ComplexSymbolFactory.Location location) {
        super("CodeScope", id, NodeEnum.PROCEDURE, location);
    }
}