package CodeGeneration.DataFlow.Blocks;

import CodeGeneration.DataFlow.Network.Nodes.Block;
import CodeGeneration.DataFlow.Network.Nodes.Blocks.AbstractBlock;
import CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operation;
import CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.BinaryOperations.UnitWiseOperations._Division;
import CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.NullaryOperation.Source;
import CodeGeneration.DataFlow.Network.Nodes.SignalNodes.Channels.ListChannel;
import LinearAlgebra.Types.Matrices.Matrix;
import LinearAlgebra.Types.Matrices.MatrixBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class A_HalfTest {


    @Test
    void test000() {
        Block block000 = new A_Half();


        Source source = new Source(
                MatrixBuilder.buildConstant(2, 2, 4)
        );

        Matrix expected = MatrixBuilder.buildConstant(2, 2, 2);

        source.connectTo(block000, Source.NULLARY_OUT_CHANNEL, "A");

        source.acceptReadySignal();

        assertEquals(expected, block000.getOutputChannels().get("out").getResult());
    }
}

class A_Half extends AbstractBlock {
    public A_Half() {
        // Channel
        this
                .addNewInputLabel("A", new ListChannel())
                .addNewOutputLabel("out", new ListChannel());

        // Blueprint
        Operation div = new _Division();

        Source twoMatrix = new Source(MatrixBuilder.buildConstant(2, 2, 2));

        this.connectTo(div, "A", _Division.BINARY_IN_A_CHANNEL);
        twoMatrix.connectTo(div, "out", _Division.BINARY_IN_B_CHANNEL);

        div.connectTo(this, _Division.BINARY_OUT_CHANNEL, "out");
    }
}
