package CodeGeneration.DataFlow.Network.Interfaces;

public interface Channel extends SignalNode {
    boolean isReady();
    void signalReady();
    void setSource(SignalNode channel);
    void addTarget(SignalNode channel);
}
