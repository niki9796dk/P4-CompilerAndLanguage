package CodeGeneration.DataFlow.Network.Interfaces;

import CodeGeneration.DataFlow.Network.GateId;

public interface Blocks extends Nodes {
    void connectTo(Blocks toBlocks, GateId fromGate, GateId toGate);
    Gates getGate(GateId gateId);
}
