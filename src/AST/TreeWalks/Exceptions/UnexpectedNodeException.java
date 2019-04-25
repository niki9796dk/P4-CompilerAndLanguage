package AST.TreeWalks.Exceptions;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.NodeEnumAble;

public class UnexpectedNodeException extends RuntimeException{
    public UnexpectedNodeException(NodeEnum nodeEnum){
        super("Unexpected node enum: " + nodeEnum);
    }

    public UnexpectedNodeException(NodeEnumAble node){
        super("Unexpected node enum: " + node.getNodeEnum());
    }
}
