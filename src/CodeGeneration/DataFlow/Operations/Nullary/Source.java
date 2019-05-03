package CodeGeneration.DataFlow.Operations.Nullary;

import CodeGeneration.DataFlow.Network.Interfaces.Block;
import LinearAlgebra.Types.Matrices.Matrix;
import LinearAlgebra.Types.Matrices.MatrixBuilder;

/**
 * A source nullary operator.
 * Contains an  immutable matrix.
 */
public class Source extends NullaryOperation {
    private final Matrix source;

    /**
     * Create a new Source NullaryOperation with a matrix.
     *
     * @param matrix The initial matrix contents.
     */
    public Source(Matrix matrix) {
        this.source = matrix;
    }

    /**
     * Create a new Source NullaryOperation with a matrix.
     *
     * @param matrix The initial matrix contents.
     */
    public Source(MatrixBuilder matrix) {
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
}
