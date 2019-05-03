package CodeGeneration.DataFlow.Blocks;

import CodeGeneration.DataFlow.Network.AbstractBlock;
import CodeGeneration.DataFlow.Network.ListChannel;
import CodeGeneration.DataFlow.Operations.BinaryOperations.UnitWiseOperations._Division;
import CodeGeneration.DataFlow.Operations.Nullary.Source;
import CodeGeneration.DataFlow.Operations.Operation;
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

