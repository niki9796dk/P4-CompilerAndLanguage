package CodeGeneration.DataFlow.Network.Nodes;

import CodeGeneration.DataFlow.Network.Node;
import CodeGeneration.DataFlow.Network.Nodes.SignalNodes.Channel;
import LinearAlgebra.Types.Matrices.Matrix;

public interface SignalNode extends Node {

    boolean isReadyBackpropagation();

    boolean isSource();

    void acceptReadySignal();
    void acceptReadyBackpropagationSignal();

    Matrix getResult();
    Matrix getResultBackpropagation(Channel channel); //Have parameter?
}
