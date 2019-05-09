package AST.TreeWalks.Exceptions;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.NodeEnumAble;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.BlockNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnexpectedNodeExceptionTest {

    @Test
    void nodeEnum() {
        assertThrows(UnexpectedNodeException.class, () -> {
            throw new UnexpectedNodeException(NodeEnum.BLOCK);
        });
    }

    @Test
    void nodeEnumAble() {
        NodeEnumAble node = new BlockNode("block");
        assertThrows(UnexpectedNodeException.class, () -> {
            throw new UnexpectedNodeException(node);
        });
    }
}