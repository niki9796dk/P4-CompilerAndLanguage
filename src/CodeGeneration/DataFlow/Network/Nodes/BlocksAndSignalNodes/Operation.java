package CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes;

import CodeGeneration.DataFlow.Network.Nodes.Block;
import CodeGeneration.DataFlow.Network.Nodes.SignalNode;

public interface Operation extends Block, SignalNode {
    void performOperation();

    void performBackpropagationOperation();
}
