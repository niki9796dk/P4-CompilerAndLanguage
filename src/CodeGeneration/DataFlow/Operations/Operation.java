package CodeGeneration.DataFlow.Operations;

import CodeGeneration.DataFlow.Network.AbstractBlock;
import CodeGeneration.DataFlow.Network.ChannelId;
import CodeGeneration.DataFlow.Network.Interfaces.Channel;

public abstract class Operation extends AbstractBlock implements CodeGeneration.DataFlow.Network.Interfaces.Operation {
    protected float value;

    @Override
    public float getValue() {
        return this.value;
    }

    @Override
    public void acceptReadySignal() {
        if (isReady()) {                         // If all inputs are ready
            this.performOperation();            // Then perform the operation
            this.getOutputChannel().signalReady(); // And signal that the output channel now is ready.
        }
    }

    abstract Channel getOutputChannel();

    private boolean isReady() {
        boolean isReady = true;

        for (Channel inputChannel : this.getInputs()) {
            isReady = isReady && inputChannel.isReady();
        }

        return isReady;
    }

    /**
     * @deprecated
     */
    protected float getInputValue(ChannelId channelId) {
        return this.getChannel(channelId).getValue();
    }

    protected float getInputValue(String channelId) {
        return this.getChannel(channelId).getValue();
    }
}
