package AST.Nodes.NodeClasses.NamedNodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.SelectorNode;
import java_cup.runtime.ComplexSymbolFactory;

/**
 * A node representing a call to a specific procedure
 * @see ParamsNode
 */
public class ProcedureCallNode extends NamedNode {
    public ProcedureCallNode(ComplexSymbolFactory.Location location) {
        super("ProcedureCall", NodeEnum.PROCEDURE_CALL, location);
    }

    public String getTargetId(){
        SelectorNode idNode = this.findFirstChildOfClass(SelectorNode.class);

        if ("this".equals(idNode.getId())){
            idNode = idNode.findFirstChildOfClass(SelectorNode.class);
        }

        return idNode.getId();
    }
}
