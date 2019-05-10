package AST.Nodes.NodeClasses.NamedNodes;

import AST.Enums.NodeEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParamsNodeTest {
    private ParamsNode paramsNode;

    @BeforeEach
    void beforeEach() {
        paramsNode = new ParamsNode();
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