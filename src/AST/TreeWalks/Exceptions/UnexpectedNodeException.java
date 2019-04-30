package AST.TreeWalks.Exceptions;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.NodeEnumAble;

/**
 * An exception thrown when a unexpected node enum is encountered.
 */
public class UnexpectedNodeException extends RuntimeException{
    /**
     * @param nodeEnum The enum of the unexpected node.
     */
    public UnexpectedNodeException(NodeEnum nodeEnum){
        super("Unexpected node enum: " + nodeEnum);
    }

    /**
     * @param node The unexpected node.
     */
    public UnexpectedNodeException(NodeEnumAble node){
        super("Unexpected node enum: " + node.getNodeEnum());
    }
}
