package CodeGeneration.DataFlow.Operations;

import CodeGeneration.DataFlow.Network.ListChannel;
import CodeGeneration.DataFlow.Network.ChannelId;
import CodeGeneration.DataFlow.Network.Interfaces.Channel;

abstract class BinaryOperation extends Operation {
    BinaryOperation() {
        // Define inputs
        Channel A = new ListChannel();
        Channel B = new ListChannel();
        A.addTarget(this);
        B.addTarget(this);

        // Define outputs
        Channel C = new ListChannel(this);

        // Store channels
        this
                .addInput(ChannelId.A, A)
                .addInput(ChannelId.B, B)
                .addOutput(ChannelId.C, C);
    }

    @Override
    Channel getOutputChannel() {
        return this.getChannel(ChannelId.C);
    }

    @Override
    public void performOperation() {
        float a = this.getInputValue(ChannelId.A);
        float b = this.getInputValue(ChannelId.B);

        this.value = operation(a, b);
    }

    abstract float operation(float a, float b);
}
