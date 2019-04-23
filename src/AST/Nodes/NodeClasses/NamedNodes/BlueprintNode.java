package AST.Nodes.NodeClasses.NamedNodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNode;

public class BlueprintNode extends NamedNode {
    public BlueprintNode() {
        super("Blueprint", NodeEnum.BLUEPRINT);
    }
}
