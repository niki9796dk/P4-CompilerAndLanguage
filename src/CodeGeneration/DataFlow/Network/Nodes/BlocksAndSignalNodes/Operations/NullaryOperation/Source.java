package CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.NullaryOperation;

import AutoGen.myMain;
import CodeGeneration.DataFlow.Network.Nodes.Blocks.AbstractBlock;
import CodeGeneration.DataFlow.Network.Nodes.SignalNodes.Channel;
import CodeGeneration.DataFlow.Network.Nodes.SignalNodes.Channels.ListChannelSource;
import DataStructures.Pair;
import LinearAlgebra.Statics.Matrices;
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
        this.addNewOutputLabel(NULLARY_OUT_CHANNEL, out);
    }

    public Source(Pair<Integer, Integer> size) {
        this();
        this.result = Matrices.randomMatrix(size.getKey(), size.getValue(), -1, 1);
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
        final int totalInputLines = AbstractBlock.configuration.getDataRows();
        final double learningRate = AbstractBlock.configuration.getLearningRate();

        Matrix derivatives = this.getOutputChannel()    // The output channel
                .getResultBackpropagation()             // Get the derivatives
                .compDivision(totalInputLines)          // Get input line average
                .mult(learningRate);                    // Multiply with learning rate

        this.result = this.result.sub(derivatives);

        if (false) {
            System.out.println("Rows: " + totalInputLines);
            System.out.println("LR: " + learningRate);
            System.out.println("Last outBack value:\n" + this.getOutputChannel().getResultBackpropagation().compDivision(totalInputLines) + "\n");
            System.out.println("Last Deri value:\n" + derivatives + "\n");
            System.out.println("##########");
        }
    }

}
