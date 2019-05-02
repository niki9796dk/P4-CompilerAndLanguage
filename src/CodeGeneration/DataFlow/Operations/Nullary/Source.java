package CodeGeneration.DataFlow.Operations.Nullary;

import CodeGeneration.DataFlow.Network.Interfaces.Block;
import LinearAlgebra.Types.Matrices.Matrix;
import LinearAlgebra.Types.Matrices.MatrixBuilder;

public class Source extends NullaryOperation {
    private final Matrix source;

    public Source(Matrix matrix) {
        this.source = matrix;
        //this.acceptReadySignal();
    }

    public Source(MatrixBuilder matrix) {
        this.source = matrix.build();
        //this.acceptReadySignal();
    }

    @Override
    protected Matrix operation() {
        return this.source;
    }

    //Should be called when there is an output
    @Override
    public void acceptReadySignal() {
        super.acceptReadySignal();
    }

    @Override
    public boolean hasNoInput() {
        return true;
    }

    @Override
    public Source connectTo(Block toBlock, String fromChannelId, String toChannelId) {
        super.connectTo(toBlock, fromChannelId, toChannelId);
        this.acceptReadySignal();
        return this;
    }
}
