package CodeGeneration.DataFlow.Network.Nodes;

import CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.NullaryOperation.Source;
import CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.UnaryOperations.MatrixOperations.Transpose;
import LinearAlgebra.Types.Matrices.MatrixBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BlockTest {

    @Test
    void getFirstInOutput() {
        Block block = new Transpose();
        assertNotNull(block.getFirstInput());
        assertNotNull(block.getFirstOutput());

        Source source = new Source(MatrixBuilder.buildConstant(1,1,1));

        source.getFirstOutput().tether(block.getFirstInput());

        source.acceptReadySignal();

        assertNotNull(block.getFirstOutput().getResult());
    }
}