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
        this.result = matrix;
    }

    /**
     * Create a new Source NullaryOperation with a matrix.
     *
     * @param matrix The initial matrix contents.
     */
    public Source(MatrixBuilder matrix) {
        this();
        this.result = matrix.build();
    }

    /**
     * @return the content matrix.
     */
    @Override
    protected Matrix operation() {
        return this.result;
    }

    @Override
    public void performBackpropagationOperation() {
        final int totalInputLines = 10; // TODO: Connect to something
        final double learningRate = 0.2;   // TODO: Connect to something

        Matrix derivatives = this.getOutputChannel()    // The output channel
                .getResultBackpropagation()             // Get the derivatives
                .compDivision(totalInputLines)          // Get input line average
                .mult(learningRate);                    // Multiply with learning rate

        this.result = this.result.sub(derivatives);
    }

}
