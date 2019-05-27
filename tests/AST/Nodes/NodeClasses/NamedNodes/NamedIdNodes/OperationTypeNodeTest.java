package AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes;

import AST.Enums.NodeEnum;
import java_cup.runtime.ComplexSymbolFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OperationTypeNodeTest {
    private OperationTypeNode operationTypeNode;

    @BeforeEach
    void beforeEach() {
        operationTypeNode = new OperationTypeNode("id", new ComplexSymbolFactory.Location(-1, -1));
    }

    @Test
    void paramsNodeEnum() {
        assertEquals(NodeEnum.OPERATION_TYPE, operationTypeNode.getNodeEnum());
    }

    @Test
    void paramsNodeName() {
        assertEquals("OperationType", operationTypeNode.getName());
    }

    @Test
    void paramsNodeId() {
        assertEquals("id", operationTypeNode.getId());
    }
}