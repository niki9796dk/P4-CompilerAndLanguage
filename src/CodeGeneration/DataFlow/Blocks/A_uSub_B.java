package CodeGeneration.DataFlow.Blocks;

import CodeGeneration.DataFlow.Network.AbstractBlock;
import CodeGeneration.DataFlow.Network.ListChannel;
import CodeGeneration.DataFlow.Operations.BinaryOperations.UnitWiseOperations._Subtraction;
import CodeGeneration.DataFlow.Operations.Operation;

/// (X, Y) -> ApBmB -> Z
/// X - ApBmB.A ->

public class A_uSub_B extends AbstractBlock {
    public A_uSub_B() {
        // Channel
        this
                .addNewInputLabel("A", new ListChannel())
                .addNewInputLabel("B", new ListChannel())
                .addNewOutputLabel("out", new ListChannel());

        // Blueprint
        Operation sub = new _Subtraction();
        this.connectTo(sub,"A", "in1");
        this.connectTo(sub,"B", "in2");

        sub.connectTo(this,"out", "out");
    }
}

