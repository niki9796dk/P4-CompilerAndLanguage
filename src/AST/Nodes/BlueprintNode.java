package AST.Nodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.NamedNode;

public class BlueprintNode extends NamedNode {
    public BlueprintNode() {
        super("Blueprint", NodeEnum.BLUEPRINT);
    }
}
