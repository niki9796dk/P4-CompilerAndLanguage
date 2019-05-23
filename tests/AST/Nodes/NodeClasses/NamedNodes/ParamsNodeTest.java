package AST.Nodes.NodeClasses.NamedNodes;

import AST.Enums.NodeEnum;
import java_cup.runtime.ComplexSymbolFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParamsNodeTest {
    private ParamsNode paramsNode;

    @BeforeEach
    void beforeEach() {
        paramsNode = new ParamsNode(new ComplexSymbolFactory.Location(-1, -1));
    }

    @Test
    void paramsNodeEnum() {
        assertEquals(NodeEnum.PARAMS, paramsNode.getNodeEnum());
    }

    @Test
    void paramsNodeName() {
        assertEquals("Params", paramsNode.getName());
    }
}