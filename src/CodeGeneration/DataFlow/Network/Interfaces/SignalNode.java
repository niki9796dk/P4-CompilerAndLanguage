package CodeGeneration.DataFlow.Network.Interfaces;

import LinearAlgebra.Types.Matrices.Matrix;

public interface SignalNode extends Node {
    void acceptReadySignal();

    Matrix getResult();
}
