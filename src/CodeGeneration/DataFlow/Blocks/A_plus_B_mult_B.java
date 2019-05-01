package CodeGeneration.DataFlow.Blocks;

import CodeGeneration.DataFlow.Network.AbstractBlock;
import CodeGeneration.DataFlow.Network.ListChannel;
import CodeGeneration.DataFlow.Network.ChannelId;
import CodeGeneration.DataFlow.Operations.Addition;
import CodeGeneration.DataFlow.Operations.Multiplication;
import CodeGeneration.DataFlow.Operations.Operation;

/// (X, Y) -> ApBmB -> Z
/// X - ApBmB.A ->

public class A_plus_B_mult_B extends AbstractBlock {
    public A_plus_B_mult_B() {
        // Channel
        this
                .addInput("A", new ListChannel())
                .addInput("B", new ListChannel())
                .addOutput("C", new ListChannel());

        // Blueprint
        Operation add = new Addition();
        this.connectTo(add,"A");
        this.connectTo(add,"B");

        Operation mult = new Multiplication();

        add.connectTo(mult, "C", "A");

        this.connectTo(mult, "B", "B");

        mult.connectTo(this,"C");
    }
}

