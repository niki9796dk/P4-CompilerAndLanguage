package CodeGeneration.DataFlow.Operations;

import CodeGeneration.DataFlow.Network.AbstractBlock;
import CodeGeneration.DataFlow.Network.Interfaces.Channel;
import CodeGeneration.utility.Print;
import Enums.AnsiColor;

public abstract class Operation extends AbstractBlock implements CodeGeneration.DataFlow.Network.Interfaces.Operation {
    protected float value;
    Print print = new Print(AnsiColor.GREEN, this.getClass().getSimpleName());

    @Override
    public float getValue() {
        print.say("getValue() -> this.value = " + value);
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

    protected float getInputValue(String channelId) {
        return this.getChannel(channelId).getValue();
    }
}
