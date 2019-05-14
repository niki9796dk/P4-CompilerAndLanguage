package CodeGeneration.DataFlow.Network.Nodes.SignalNodes.BounceNodes;

import CodeGeneration.DataFlow.Network.Nodes.Block;
import CodeGeneration.DataFlow.Network.Nodes.SignalNodes.BounceNode;
import CodeGeneration.DataFlow.Network.Nodes.SignalNodes.Channel;
import LinearAlgebra.Types.Matrices.Matrix;

public abstract class AbstractBounceNode implements BounceNode {

    @Override
    public Matrix getResult() {
        throw new NullPointerException("This node cannot have feedforward results");
    }

    @Override
    public Matrix getResultBackpropagation(Channel channel) {
        throw new NullPointerException("This node cannot have backpropagation results");
    }
}
