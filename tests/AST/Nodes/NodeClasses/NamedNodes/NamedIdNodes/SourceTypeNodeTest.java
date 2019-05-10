package AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes;

import AST.Enums.NodeEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SourceTypeNodeTest {
    private SourceTypeNode sourceTypeNode;

    @BeforeEach
    void beforeEach() {
        sourceTypeNode = new SourceTypeNode("id");
    }

    @Test
    void paramsNodeEnum() {
        assertEquals(NodeEnum.SOURCE_TYPE, sourceTypeNode.getNodeEnum());
    }

    @Test
    void paramsNodeName() {
        assertEquals("SourceType", sourceTypeNode.getName());
    }

    @Test
    void paramsNodeId() {
        assertEquals("id", sourceTypeNode.getId());
    }

}