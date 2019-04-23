package AST.Nodes.NodeClasses.NamedNodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNode;

public class ParamsNode extends NamedNode {
    public ParamsNode() {
        super("Params", NodeEnum.PARAMS);
    }
}
