package CodeGeneration.DataFlow.Executions;

import CodeGeneration.DataFlow.Network.Nodes.Block;
import CodeGeneration.DataFlow.Network.Nodes.SignalNodes.BounceNode;
import CodeGeneration.DataFlow.Network.Nodes.SignalNodes.BounceNodes.FeedforwardBounce;
import CodeGeneration.DataFlow.Network.Nodes.SignalNodes.BounceNodes.backpropagationBounce;
import LinearAlgebra.Types.Matrices.MatrixBuilder;

public class MainBlock {
    public static void main(String[] args) {
        Block block = new BlockWrapper();
        BounceNode feedForward = new FeedforwardBounce(MatrixBuilder.buildConstant(2, 2, 10));
        BounceNode backProp = new backpropagationBounce(MatrixBuilder.buildConstant(2, 2, 10));

        feedForward.connectToMainBlock(block);
        backProp.connectToMainBlock(block);

        for (int i = 0; i < 2; i++) {
            feedForward.acceptReadySignal();
        }
    }
}
