package CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.NullaryOperation;

import CodeGeneration.DataFlow.Network.Nodes.Block;
import CodeGeneration.utility.Print;
import DataStructures.Pair;
import Enums.AnsiColor;
import LinearAlgebra.Types.Matrices.Matrix;
import LinearAlgebra.Types.Matrices.MatrixBuilder;

/**
 * An input nullary operator.
 * Contains a mutable matrix.
 * Whenever its input is changed, it will call acceptReadySignal()
 */
public class Input extends Source {

    /**
     * Create a new input NullaryOperation with a matrix.
     *
     */

    public Input(Pair<Integer, Integer> size) {
        super(size);
    }

    public Input(Matrix matrix) {
        super(matrix);
    }

    /**
     * Create a new input NullaryOperation with a matrixBuilder.
     *
     * @param matrix The initial matrix contents.
     */
    public Input(MatrixBuilder matrix) {
        super(matrix);
    }


    /**
     * Change the contents of the operation.
     *
     * @param source The new matrix contents.
     * @return A reference of this object.
     */
    @Deprecated
    public Input setInput(MatrixBuilder source) {
        return this.setInput(source.buildDenseMatrix());
    }

    /**
     * Change the contents of the operation.
     *
     * @param source The new matrix contents.
     * @return A reference of this object.
     */
    public Input setInput(Matrix source) {
        this.result = source;
        this.acceptReadySignal();
        return this;
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
        super.connectTo(toBlock, fromChannelId, toChannelId);
        this.acceptReadySignal();
        return this;
    }
}
