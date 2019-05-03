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

    @Override
    public Source connectTo(Block toBlock, String fromChannelId, String toChannelId) {
        super.connectTo(toBlock, fromChannelId, toChannelId);
        this.acceptReadySignal();
        return this;
    }
}
