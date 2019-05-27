package AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes;

import AST.Enums.NodeEnum;
import java_cup.runtime.ComplexSymbolFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OutChannelTypeNodeTest {

    private OutChannelTypeNode outChannelTypeNode;

    @BeforeEach
    void beforeEach() {
        outChannelTypeNode = new OutChannelTypeNode("id", new ComplexSymbolFactory.Location(-1, -1));
    }

    @Test
    void paramsNodeEnum() {
        assertEquals(NodeEnum.CHANNEL_OUT_TYPE, outChannelTypeNode.getNodeEnum());
    }

    @Test
    void paramsNodeName() {
        assertEquals("OutChannelType", outChannelTypeNode.getName());
    }

    @Test
    void paramsNodeId() {
        assertEquals("id", outChannelTypeNode.getId());
    }

}