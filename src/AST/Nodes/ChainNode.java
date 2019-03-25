package AST.Nodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.NamedNode;

public class ChainNode extends NamedNode {
    public ChainNode() {
        super("Chain", NodeEnum.CHAIN);
    }
}