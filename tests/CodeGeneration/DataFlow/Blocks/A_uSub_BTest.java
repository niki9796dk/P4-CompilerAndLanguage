package CodeGeneration.DataFlow.Blocks;

import CodeGeneration.DataFlow.Network.Nodes.Block;
import CodeGeneration.DataFlow.Network.Nodes.Blocks.AbstractBlock;
import CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.AbstractOperation;
import CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.BinaryOperations.UnitWiseOperations._Subtraction;
import CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.NullaryOperation.Source;
import CodeGeneration.DataFlow.Network.Nodes.SignalNodes.Channels.ListChannel;
import CodeGeneration.utility.Print;
import Enums.AnsiColor;
import LinearAlgebra.Types.Matrices.Matrix;
import LinearAlgebra.Types.Matrices.MatrixBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class A_uSub_BTest {
    @Test
    void test000() {
        Block block000 = new A_uSub_B();


        Source source000 = new Source(
                new MatrixBuilder()
                        .addRow(1, 2)
                        .addRow(4, 5)
        );

        Source source001 = new Source(
                new MatrixBuilder()
                        .addRow(3, 2)
                        .addRow(4, 3)
        );

        Matrix expected = new MatrixBuilder()
                .addRow(-2, 0)
                .addRow(0, 2)
                .build();

        source000.connectTo(block000, "out", "A");

        source001.connectTo(block000, "out", "B");


        Print.echo(AnsiColor.PURPLE, "" + block000.getOutputChannels().get("out").getResult());

        assertEquals(expected, block000.getOutputChannels().get("out").getResult());
    }
}

class A_uSub_B extends AbstractBlock {
    public A_uSub_B() {
        // Channel
        this
                .addNewInputLabel("A", new ListChannel())
                .addNewInputLabel("B", new ListChannel())
                .addNewOutputLabel("out", new ListChannel());

        // Blueprint
        AbstractOperation sub = new _Subtraction();
        this.connectTo(sub, "A", "in1");
        this.connectTo(sub, "B", "in2");

        sub.connectTo(this, "out", "out");
    }
}