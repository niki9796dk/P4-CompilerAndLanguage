package AST.Nodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.NamedNode;

public class ParamsNode extends NamedNode {
    public ParamsNode() {
        super("Params", NodeEnum.PARAMS);
    }
}
