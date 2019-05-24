package AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;
import java_cup.runtime.ComplexSymbolFactory;

import java.util.Objects;


public class DummyNode extends NamedIdNode {
    public DummyNode() {
        super("DummyNode", "Dummy", NodeEnum.DUMMY, new ComplexSymbolFactory.Location(-1, -1));
    }
}