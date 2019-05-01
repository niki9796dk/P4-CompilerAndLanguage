package CodeGeneration.DataFlow.Network.Interfaces;

public interface Channel extends SignalNode {
    boolean isReady();
    void signalReady();
    void setSource(SignalNode gate);
    void addTarget(SignalNode gate);
}
