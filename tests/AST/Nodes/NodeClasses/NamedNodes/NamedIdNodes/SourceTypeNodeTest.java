package AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes;

import AST.Enums.NodeEnum;
import CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.NullaryOperation.Source;
import DataStructures.Pair;
import java_cup.runtime.ComplexSymbolFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SourceTypeNodeTest {
    private SourceTypeNode sourceTypeNode;

    @BeforeEach
    void beforeEach() {
        sourceTypeNode = new SourceTypeNode("id", new ComplexSymbolFactory.Location(-1, -1));
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

    @Test
    void sizeConstructor(){
        Source source = new Source(new Pair<>(3,2));

        assertEquals(3,source.getResult().getRows());
        assertEquals(2,source.getResult().getColumns());
    }

}