package CodeGeneration.DataFlow.Blocks;

import CodeGeneration.DataFlow.Network.Nodes.Block;
import CodeGeneration.DataFlow.Network.Nodes.Blocks.AbstractBlock;
import CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.AbstractOperation;
import CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.BinaryOperations.MatrixOperations.Multiplication;
import CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.BinaryOperations.UnitWiseOperations._Addition;
import CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.NullaryOperation.Input;
import CodeGeneration.DataFlow.Network.Nodes.SignalNodes.Channels.ListChannel;
import CodeGeneration.utility.Print;
import Enums.AnsiColor;
import LinearAlgebra.Types.Matrices.Matrix;
import LinearAlgebra.Types.Matrices.MatrixBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class A_plus_B_mult_BTest {
    @Test
    void test000() {
        Block block000 = new A_plus_B_mult_B();


        Input source000 = new Input(
                new MatrixBuilder()
                        .addRow(1, 2)
                        .addRow(4, 5)
        );

        Input source001 = new Input(
                new MatrixBuilder()
                        .addRow(3, 2)
                        .addRow(4, 3)
        );

        Matrix expected = new MatrixBuilder()
                .addRow(28, 20)
                .addRow(56, 40)
                .build();

        source000.connectTo(block000, "out", "A");

        source001.connectTo(block000, "out", "B");


        Print.echo(AnsiColor.PURPLE, "" + block000.getOutputChannels().get("out").getResult());

        assertEquals(expected, block000.getOutputChannels().get("out").getResult());
    }
}

class A_plus_B_mult_B extends AbstractBlock {
    public A_plus_B_mult_B() {
        // Channel
        this.addNewInputLabel("A", new ListChannel());
        this.addNewInputLabel("B", new ListChannel());
        this.addNewOutputLabel("out", new ListChannel());

        // Blueprint
        AbstractOperation add = new _Addition();
        AbstractOperation mult = new Multiplication();

        this.connectTo(add, "A", "in1");
        this.connectTo(add, "B", "in2");

        add.connectTo(mult, "out", "in1");

        this.connectTo(mult, "B", "in2");

        mult.connectTo(this, "out", "out");
    }
}