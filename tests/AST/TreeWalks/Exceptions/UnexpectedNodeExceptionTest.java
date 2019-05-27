package AST.TreeWalks.Exceptions;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.NodeEnumAble;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.BlockNode;
import CompilerExceptions.UnexpectedNodeException;
import java_cup.runtime.ComplexSymbolFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class UnexpectedNodeExceptionTest {

    @Test
    void nodeEnum() {
        assertThrows(UnexpectedNodeException.class, () -> {
            throw new UnexpectedNodeException(NodeEnum.BLOCK);
        });
    }

    @Test
    void nodeEnumAble() {
        NodeEnumAble node = new BlockNode("block", new ComplexSymbolFactory.Location(-1, -1));
        assertThrows(UnexpectedNodeException.class, () -> {
            throw new UnexpectedNodeException(node);
        });
    }
}