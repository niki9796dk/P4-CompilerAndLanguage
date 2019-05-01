package CodeGeneration.DataFlow.Blocks;

import CodeGeneration.DataFlow.Network.AbstractBlock;
import CodeGeneration.DataFlow.Network.AbstractChannel;
import CodeGeneration.DataFlow.Network.ChannelId;
import CodeGeneration.DataFlow.Operations.Addition;
import CodeGeneration.DataFlow.Operations.Multiplication;
import CodeGeneration.DataFlow.Operations.Operation;

public class A_plus_B_mult_B extends AbstractBlock {
    public A_plus_B_mult_B() {
        // Channel
        this
                .addInput(ChannelId.A, new AbstractChannel())
                .addInput(ChannelId.B, new AbstractChannel())
                .addOutput(ChannelId.C, new AbstractChannel());

        // Blueprint
        Operation add = new Addition();

        this.connectTo(add, ChannelId.A, ChannelId.A);

        this
                .connectTo(
                add,
                ChannelId.B,
                ChannelId.B);

        Operation mult = new Multiplication();

        add.connectTo(mult, ChannelId.C, ChannelId.A);

        this.connectTo(mult, ChannelId.B, ChannelId.B);

        mult.connectTo(this, ChannelId.C, ChannelId.C);
    }
}

