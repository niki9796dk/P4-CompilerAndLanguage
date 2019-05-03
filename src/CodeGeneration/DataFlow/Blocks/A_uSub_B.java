package CodeGeneration.DataFlow.Blocks;

import CodeGeneration.DataFlow.Network.Nodes.Blocks.AbstractBlock;
import CodeGeneration.DataFlow.Network.Nodes.SignalNodes.Channels.ListChannel;
import CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.BinaryOperations.UnitWiseOperations._Subtraction;
import CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.AbstractOperation;

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
        AbstractOperation sub = new _Subtraction();
        this.connectTo(sub, "A", "in1");
        this.connectTo(sub, "B", "in2");

        sub.connectTo(this, "out", "out");
    }
}

