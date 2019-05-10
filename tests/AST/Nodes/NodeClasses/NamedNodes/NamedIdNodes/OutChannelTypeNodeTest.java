package AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes;

import AST.Enums.NodeEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OutChannelTypeNodeTest {

    private OutChannelTypeNode outChannelTypeNode;

    @BeforeEach
    void beforeEach() {
        outChannelTypeNode = new OutChannelTypeNode("id");
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