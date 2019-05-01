package CodeGeneration.DataFlow.Network.Interfaces;

public interface Gates extends SignalNodes {
    boolean isReady();
    void signalReady();
    void setSource(SignalNodes gate);
    void addTarget(SignalNodes gate);
}
