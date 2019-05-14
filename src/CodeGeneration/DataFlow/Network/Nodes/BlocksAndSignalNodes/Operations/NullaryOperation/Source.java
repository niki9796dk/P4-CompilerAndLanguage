package CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.NullaryOperation;

import CodeGeneration.DataFlow.Network.Nodes.Block;
import CodeGeneration.DataFlow.Network.Nodes.SignalNodes.Channel;
import CodeGeneration.DataFlow.Network.Nodes.SignalNodes.Channels.ListChannel;
import CodeGeneration.DataFlow.Network.Nodes.SignalNodes.Channels.ListChannelSource;
import LinearAlgebra.Types.Matrices.Matrix;
import LinearAlgebra.Types.Matrices.MatrixBuilder;

/**
 * A source nullary operator.
 * Contains an  immutable matrix.
 */
public class Source extends NullaryAbstractOperation {
    private Matrix source;

    private Source() {
        this.getOutputChannels().clear(); // Reset all output channels from super classes

        // Define outputs
        Channel out = new ListChannelSource(this); // Define as channel source

        // Store channels
        this.addNewOutputLabel("out", out);
    }

    /**
     * Create a new Source NullaryOperation with a matrix.
     *
     * @param matrix The initial matrix contents.
     */
    public Source(Matrix matrix) {
        this();
        this.source = matrix;
    }

    /**
     * Create a new Source NullaryOperation with a matrix.
     *
     * @param matrix The initial matrix contents.
     */
    public Source(MatrixBuilder matrix) {
        this();
        this.source = matrix.build();
    }

    /**
     * @return the content matrix.
     */
    @Override
    protected Matrix operation() {
        return this.source;
    }

    /**
     * Connect the Input to a block, and signal that this block is ready.
     * @param toBlock       the block to connect to
     * @param fromChannelId the output channel id from this where connection starts
     * @param toChannelId   the input channel id from toBlock where connection ends
     * @return a reference to this object.
     */
    @Override
    public Source connectTo(Block toBlock, String fromChannelId, String toChannelId) {
        super.connectTo(toBlock, fromChannelId, toChannelId);
        this.acceptReadySignal();
        return this;
    }

    @Override
    public void performBackpropagationOperation() {
        final int totalInputLines = 10; // TODO: Connect to something
        final double learningRate = 0.2;   // TODO: Connect to something

        Matrix derivatives = this.getOutputChannel()    // The output channel
                .getResultBackpropagation()             // Get the derivatives
                .compDivision(totalInputLines)          // Get input line average
                .mult(learningRate);                    // Multiply with learning rate

        this.source = this.source.sub(derivatives);
    }
}
