package CodeGeneration.DataFlow.Network.Interfaces;

public interface SignalNode extends Node {
    void acceptReadySignal();
    float getResult();
}
