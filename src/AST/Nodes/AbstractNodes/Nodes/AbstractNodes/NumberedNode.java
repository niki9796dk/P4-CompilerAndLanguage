package AST.Nodes.AbstractNodes.Nodes.AbstractNodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNode;

public abstract class NumberedNode extends AbstractNode {
    private int number = -1;

    private NodeEnum nodeEnum;

    public NumberedNode(int number, NodeEnum nodeEnum) {
        this(nodeEnum);
        this.setNumber(number);
    }

    public NumberedNode(NodeEnum nodeEnum) {
        this.nodeEnum = nodeEnum;
    }

    public int getNumber() {
        return number;
    }

    public NodeEnum getNodeEnum() {
        return nodeEnum;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "[" + this.number + "]";
    }
}
