package CodeGeneration.DataFlow.Network.Interfaces;

import LinearAlgebra.Types.Matrices.Matrix;

public interface SignalNode extends Node {
    void acceptReadySignal();

    //void acceptBackSignal();

    Matrix getResult();
}
