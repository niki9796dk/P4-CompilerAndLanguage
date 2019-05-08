package CodeGeneration.DataFlow.Network.Nodes;

import CodeGeneration.DataFlow.Network.Node;
import LinearAlgebra.Types.Matrices.Matrix;

public interface SignalNode extends Node {
    void acceptReadySignal();

    Matrix getResult();
}
