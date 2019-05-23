package AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes;

import AST.Enums.NodeEnum;
import java_cup.runtime.ComplexSymbolFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InChannelTypeNodeTest {

    private InChannelTypeNode inChannelTypeNode;

    @BeforeEach
    void beforeEach() {
        inChannelTypeNode = new InChannelTypeNode("id", new ComplexSymbolFactory.Location(-1, -1));
    }

    @Test
    void paramsNodeEnum() {
        assertEquals(NodeEnum.CHANNEL_IN_TYPE, inChannelTypeNode.getNodeEnum());
    }

    @Test
    void paramsNodeName() {
        assertEquals("InChannelType", inChannelTypeNode.getName());
    }

    @Test
    void paramsNodeId() {
        assertEquals("id", inChannelTypeNode.getId());
    }

}