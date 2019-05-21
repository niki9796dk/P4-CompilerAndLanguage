package CodeGeneration.DataFlow.Network.Nodes.SignalNodes.BounceNodes;

import CodeGeneration.DataFlow.Network.Nodes.Block;
import CodeGeneration.DataFlow.Network.Nodes.SignalNodes.BounceNode;
import CodeGeneration.DataFlow.Network.Nodes.SignalNodes.Channel;
import CodeGeneration.utility.Print;
import Enums.AnsiColor;
import LinearAlgebra.Types.Matrices.Matrix;

public class FeedforwardBounce extends AbstractBounceNode {
    private static Print print = new Print(AnsiColor.BLUE, "FeedForwardBouncer").mute();

    private Channel target;
    private Matrix inputs;

    public FeedforwardBounce(Matrix inputs) {
        this.inputs = inputs;
    }

    @Override
    public boolean isReadyBackpropagation() {
        return false;
    }

    @Override
    public void acceptReadySignal() {
        print.say("Signal has been started.");

        // Start the bounce
        this.target.acceptReadySignal();
    }

    @Override
    public void acceptReadyBackpropagationSignal() {
        // Perform the required bounce logic
        print.say("Signal has returned to the start.");

        // Do not rebounce, otherwise a stackOverflow will happen.
    }

    @Override
    public Matrix getResult() {
        return this.inputs;
    }

    @Override
    public void connectToMainBlock(Block mainBlock) {
        Channel[] mainBlockInputs = mainBlock.getInputChannels().values().toArray(new Channel[0]);

        if (mainBlockInputs.length != 1) {
            throw new IllegalArgumentException("The main block MUST have only one input and output!");
        }

        Channel inputChannel = mainBlockInputs[0];

        if (inputChannel == null) {
            throw new NullPointerException("The input channel was null");
        }

        this.target = inputChannel;     // Connect this to that
        inputChannel.setSource(this);   // Connect that to this
    }
}
