package CodeGeneration.DataFlow.Network.Nodes.SignalNodes;

import CodeGeneration.DataFlow.Network.Nodes.SignalNode;

import java.util.Collection;

public interface Channel extends SignalNode {
    boolean isReady();

    void sendReadySignals();


    SignalNode setSource(SignalNode channel);


    SignalNode addTarget(SignalNode channel);

    SignalNode getSource();

    void tether(Channel that);

    Collection<SignalNode> getTargets();
}
