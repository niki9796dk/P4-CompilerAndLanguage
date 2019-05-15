package CodeGeneration.DataFlow.Network.Nodes.SignalNodes;

import CodeGeneration.DataFlow.Network.Nodes.Block;
import CodeGeneration.DataFlow.Network.Nodes.SignalNode;

public interface BounceNode extends SignalNode {
    void connectToMainBlock(Block mainBlock);
}
