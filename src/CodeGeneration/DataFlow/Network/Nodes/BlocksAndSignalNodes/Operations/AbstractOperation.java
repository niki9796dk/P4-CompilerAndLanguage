package CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations;

import CodeGeneration.DataFlow.Network.Nodes.Blocks.AbstractBlock;
import CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operation;
import CodeGeneration.DataFlow.Network.Nodes.SignalNodes.Channel;
import CodeGeneration.utility.Print;
import Enums.AnsiColor;
import LinearAlgebra.Types.Matrices.Matrix;

public abstract class AbstractOperation extends AbstractBlock implements Operation {
    private boolean isReady = false;
    private boolean isReadyBackpropagation = false;
    protected Matrix result;
    protected Matrix[] resultBackpropagation = new Matrix[2];
    protected Print print = new Print(AnsiColor.GREEN, "Operation." + this.getClass().getSimpleName());

    /**
     * Get result of operation
     *
     * @return result as float
     */
    @Override
    public Matrix getResult() {
        //print.say("getResult() -> this.result = " + this.result);
        return result;
    }

    /**
     * Given that all inputs of the operation are isReady, perform operation and set outChannel signal to isReady
     */
    @Override
    public void acceptReadySignal() {
        if (this.isReady()) {                       // If all inputs are isReady
            this.performOperation();                // Then perform the operation
            this.getOutputChannel().sendReadySignals();  // And signal that the output channel now is isReady.
        }
    }

    protected abstract Channel getOutputChannel();

    /**
     * Checks if operation is isReady to be performed
     *
     * @return boolean value of whether all operation inputs are isReady, or there are no inputs.
     */
    private boolean isReady() {
        if (this.isReady)
            return true;

        if (this.getInputs().isEmpty())
            return (this.isReady = true);

        for (Channel inputChannel : this.getInputs())
            if (!inputChannel.isReady())
                return false;

        return (this.isReady = true);
    }

    /**
     * Get the output of the given channel
     *
     * @param channelId channel to get output from
     * @return Matrix value from output of channel
     */
    protected Matrix getInputValue(String channelId) {
        return this.getChannel(channelId).getResult();
    }

    @Override
    public void acceptReadyBackpropagationSignal() {
        if (this.isReadyBackpropagation()) {                       // If all inputs are isReady
            this.performBackpropagationOperation();                // Then perform the operation

            for (Channel inputChannel : this.getInputs())
                inputChannel.sendReadyBackpropagationSignals();  // And signal that the output channel now is isReady.
        }
    }


    @Override
    public boolean isReadyBackpropagation() {
        if (this.isReadyBackpropagation)
            return true;

        if (this.getOutputs().isEmpty())
            return (this.isReadyBackpropagation = true);

        for (Channel outputChannel : this.getOutputs())
            if (!outputChannel.isReady())
                return false;

        return (this.isReadyBackpropagation = true);
    }
}
