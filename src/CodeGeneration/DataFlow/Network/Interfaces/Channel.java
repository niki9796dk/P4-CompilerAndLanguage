package CodeGeneration.DataFlow.Network.Interfaces;

public interface Channel extends SignalNode {
    boolean isReady();
    void signalReady();
    SignalNode setSource(SignalNode channel);
    SignalNode addTarget(SignalNode channel);
    SignalNode getSource();
}
