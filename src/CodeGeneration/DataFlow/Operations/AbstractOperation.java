package CodeGeneration.DataFlow.Operations;

import CodeGeneration.DataFlow.Network.AbstractBlock;
import CodeGeneration.DataFlow.Network.Interfaces.Channel;
import CodeGeneration.DataFlow.Network.Interfaces.Operation;
import CodeGeneration.DataFlow.Network.Interfaces.SignalNode;
import CodeGeneration.utility.Print;
import Enums.AnsiColor;
import LinearAlgebra.Types.Matrices.Matrix;

public abstract class AbstractOperation extends AbstractBlock implements Operation {
    protected Matrix result;
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
     * Given that all inputs of the operation are ready, perform operation and set outChannel signal to ready
     */
    @Override
    public void acceptReadySignal() {
        print.say("acceptReadySignal()");
        if (this.isReady()) {// If all inputs are ready
            this.performOperation();                // Then perform the operation
            this.getOutputChannel().signalReady();  // And signal that the output channel now is ready.
        }
    }

    protected abstract Channel getOutputChannel();

    /**
     * Checks if operation is ready to be performed
     *
     * @return boolean value of whether all operation inputs are ready, or there are no inputs.
     */
    private boolean isReady() {
        if(this.getInputs().isEmpty())
            return true;

        for (Channel inputChannel : this.getInputs())
            if(!inputChannel.isReady())
                return false;

        return true;
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

    /*
    @Override
    public void traverseBackwards() {
        print.say("Operation traverseBackwards!");
        for(SignalNode n: this.getInputs())
            n.traverseBackwards();
    }
    */
}
