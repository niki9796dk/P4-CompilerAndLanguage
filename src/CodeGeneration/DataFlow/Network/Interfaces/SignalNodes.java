package CodeGeneration.DataFlow.Network.Interfaces;

public interface SignalNodes extends Nodes {
    void acceptReadySignal();
    float getValue();
}
