package CodeGeneration.DataFlow.Network.Nodes.SignalNodes.BounceNodes;

import CodeGeneration.DataFlow.Network.Nodes.Block;
import CodeGeneration.DataFlow.Network.Nodes.SignalNodes.BounceNode;
import CodeGeneration.DataFlow.Network.Nodes.SignalNodes.Channel;
import LinearAlgebra.Types.Matrices.Matrix;

public abstract class AbstractBounceNode implements BounceNode {

    protected int touchCounter = 0;

    @Override
    public Matrix getResult() {
        throw new NullPointerException("This node cannot have feedforward results");
    }

    @Override
    public Matrix getResultBackpropagation(Channel channel) {
        throw new NullPointerException("This node cannot have backpropagation results");
    }

    @Override
    public boolean isSource() {
        return false;
    }

    @Override
    public boolean hasBeenTouched() {
        return this.touchCounter > 0;
    }

    @Override
    public void forgetTouch() {
        this.touchCounter = 0;
    }

    @Override
    public int getTotalTouches() {
        return this.touchCounter;
    }
}
