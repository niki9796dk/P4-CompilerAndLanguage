package CodeGeneration.DataFlow.Network.Interfaces;

import LinearAlgebra.Types.Matrices.Matrix;

public interface SignalNode extends Node {
    void acceptReadySignal();

    Matrix getResult();

    /**
     * If the operation is predefined, often meaning it has no inputs it depends on.
     */
    default boolean hasNoInput() {
        return false;
    }

    ;

}
