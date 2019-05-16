package CodeGeneration.DataFlow.Blocks;

import CodeGeneration.DataFlow.Network.Nodes.Blocks.AbstractBlock;
import CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operation;
import CodeGeneration.DataFlow.Network.Nodes.SignalNodes.Channels.ListChannel;
import CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.BinaryOperations.UnitWiseOperations._Division;
import CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.NullaryOperation.Source;
import LinearAlgebra.Types.Matrices.MatrixBuilder;

/// (X, Y) -> ApBmB -> Z
/// X - ApBmB.A ->

public class A_Half extends AbstractBlock {
    public A_Half() {
        // Channel
        this
                .addNewInputLabel("A", new ListChannel())
                .addNewOutputLabel("out", new ListChannel());

        // Blueprint
        Operation div = new _Division();

        Source twoMatrix = new Source(MatrixBuilder.buildConstant(2, 2, 2));

        this.connectTo(div, "A", "in1");
        twoMatrix.connectTo(div, "out", "in2");

        div.connectTo(this, "out", "out");
    }
}

