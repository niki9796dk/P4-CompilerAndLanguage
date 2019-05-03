package CodeGeneration.DataFlow.Blocks;

import CodeGeneration.DataFlow.Network.AbstractBlock;
import CodeGeneration.DataFlow.Network.ListChannel;
import CodeGeneration.DataFlow.Operations.BinaryOperations.MatrixOperations.Multiplication;
import CodeGeneration.DataFlow.Operations.BinaryOperations.UnitWiseOperations._Addition;
import CodeGeneration.DataFlow.Operations.Operation;

/// (X, Y) -> ApBmB -> Z
/// X - ApBmB.A ->

public class A_plus_B_mult_B extends AbstractBlock {
    public A_plus_B_mult_B() {
        // Channel
        this.addNewInputLabel("A", new ListChannel());
        this.addNewInputLabel("B", new ListChannel());
        this.addNewOutputLabel("out", new ListChannel());

        // Blueprint
        Operation add = new _Addition();
        Operation mult = new Multiplication();

        this.connectTo(add, "A", "in1");
        this.connectTo(add, "B", "in2");

        add.connectTo(mult, "out", "in1");

        this.connectTo(mult, "B", "in2");

        mult.connectTo(this, "out", "out");
    }
}

