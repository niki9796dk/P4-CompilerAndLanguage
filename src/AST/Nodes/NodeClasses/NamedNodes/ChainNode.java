package AST.Nodes.NodeClasses.NamedNodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNode;

public class ChainNode extends NamedNode {
    public ChainNode() {
        super("Chain", NodeEnum.CHAIN);
    }
}