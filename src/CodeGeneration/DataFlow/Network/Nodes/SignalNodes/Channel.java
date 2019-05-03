package CodeGeneration.DataFlow.Network.Nodes.SignalNodes;

import CodeGeneration.DataFlow.Network.Nodes.SignalNode;

import java.util.Collection;

public interface Channel extends SignalNode {
    boolean isReady();

    void signalReady();


    SignalNode setSource(SignalNode channel);


    SignalNode addTarget(SignalNode channel);

    SignalNode getSource();

    Collection<SignalNode> getTargets();

    //boolean flip();
}
