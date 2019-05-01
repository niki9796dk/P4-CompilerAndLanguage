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
            this.getOutputGate().signalReady(); // And signal that the output gate now is ready.
        }
    }

    abstract Channel getOutputGate();

    private boolean isReady() {
        boolean isReady = true;

        for (Channel inputGate : this.getInputs()) {
            isReady = isReady && inputGate.isReady();
        }

        return isReady;
    }

    protected float getInputValue(ChannelId channelId) {
        return this.getGate(channelId).getValue();
    }
}
