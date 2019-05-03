package CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.NullaryOperation;

import CodeGeneration.DataFlow.Network.Nodes.Block;
import LinearAlgebra.Types.Matrices.Matrix;
import LinearAlgebra.Types.Matrices.MatrixBuilder;

/**
 * An input nullary operator.
 * Contains a mutable matrix.
 * Whenever its input is changed, it will call acceptReadySignal()
 */
public class Input extends NullaryAbstractOperation {
    private Matrix input;

    /**
     * Create an empty Input object with NULL as its contents.
     */
    public Input() {
        input = null;
    }

    /**
     * Create a new input NullaryOperation with a matrix.
     *
     * @param matrix The initial matrix contents.
     */
    public Input(Matrix matrix) {
        this.setInput(matrix);
    }

    /**
     * Create a new input NullaryOperation with a matrixBuilder.
     *
     * @param matrix The initial matrix contents.
     */
    public Input(MatrixBuilder matrix) {
        this.setInput(matrix.build());
    }


    /**
     * Change the contents of the operation.
     *
     * @param source The new matrix contents.
     * @return A reference of this object.
     */
    public Input setInput(MatrixBuilder source) {
        return this.setInput(source.build());
    }

    /**
     * Change the contents of the operation.
     *
     * @param source The new matrix contents.
     * @return A reference of this object.
     */
    public Input setInput(Matrix source) {
        this.input = source;
        this.acceptReadySignal();
        return this;
    }


    /**
     * Perform a nullary operation.
     *
     * @return The result of the operation.
     */
    @Override
    protected Matrix operation() {
        return this.input;
    }


    /**
     * Connect the Input to a block, and signal that this block is ready.
     *
     * @param toBlock       the block to connect to
     * @param fromChannelId the output channel id from this where connection starts
     * @param toChannelId   the input channel id from toBlock where connection ends
     * @return a reference to this object.
     * @throws NullPointerException if the contents are null.
     */
    @Override
    public Input connectTo(Block toBlock, String fromChannelId, String toChannelId) {
        if (this.input == null)
            throw new NullPointerException("Contents of input is NULL!");

        super.connectTo(toBlock, fromChannelId, toChannelId);
        this.acceptReadySignal();
        return this;
    }
}
