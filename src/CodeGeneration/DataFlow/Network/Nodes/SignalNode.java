package CodeGeneration.DataFlow.Network.Nodes;

import CodeGeneration.DataFlow.Network.Node;
import LinearAlgebra.Types.Matrices.Matrix;

public interface SignalNode extends Node {

    boolean isReadyBackpropagation();

    void acceptReadySignal();
    void acceptReadyBackpropagationSignal();

    Matrix getResult();
    Matrix getResultBackpropagation(); //Have parameter?
}
