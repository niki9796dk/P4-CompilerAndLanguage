package CodeGeneration.DataFlow.Network.Nodes.SignalNodes.BounceNodes;

import CodeGeneration.DataFlow.Network.Nodes.Block;
import CodeGeneration.DataFlow.Network.Nodes.SignalNodes.Channel;
import LinearAlgebra.Types.Matrices.Matrix;

public class FeedforwardBounce extends AbstractBounceNode {

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
        // Start the bounce
        this.target.acceptReadySignal();
    }

    @Override
    public void acceptReadyBackpropagationSignal() {
        // Perform the required bounce logic
        this.touchCounter++;

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

    @Override
    public void releaseFromMainBlock() {
        this.target.clearSource();
        this.target = null;
    }
}
