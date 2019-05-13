package AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;

import java.util.Objects;


public class DummyNode extends NamedIdNode {
    public DummyNode() {
        super("DummyNode", "Dummy", NodeEnum.DUMMY);
    }
}