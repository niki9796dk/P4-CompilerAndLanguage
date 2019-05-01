package CodeGeneration.DataFlow.Operations;

import CodeGeneration.DataFlow.Network.AbstractBlock;
import CodeGeneration.DataFlow.Network.ChannelId;
import CodeGeneration.DataFlow.Network.Interfaces.Channel;
import CodeGeneration.utility.Print;
import Enums.AnsiColor;

public abstract class Operation extends AbstractBlock implements CodeGeneration.DataFlow.Network.Interfaces.Operation {
    protected float result;
    Print print = new Print(AnsiColor.GREEN, this.getClass().getSimpleName());

    /**
     * Get result of operation
     * @return result as float
     */
    @Override
    public float getResult() {
        print.say("getResult() -> this.result = " + result);
        return this.result;
    }

    /**
     * Given that all inputs of the operation are ready, perform operation and set outChannel signal to ready
     */
    @Override
    public void acceptReadySignal() {
        if (isReady()) {                            // If all inputs are ready
            this.performOperation();                // Then perform the operation
            this.getOutputChannel().signalReady();  // And signal that the output channel now is ready.
        }
    }

    abstract Channel getOutputChannel();

    /**
     * Checks if operation is ready to be performed
     * @return boolean value of whether all operation inputs are ready
     */
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
        return this.getChannel(channelId).getResult();
    }

    /**
     * Get the output of the given channel
     * @param channelId channel to get output from
     * @return float value from output of channel
     */
    protected float getInputValue(String channelId) {
        return this.getChannel(channelId).getResult();
    }
}
