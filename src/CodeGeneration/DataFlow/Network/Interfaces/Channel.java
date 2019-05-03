package CodeGeneration.DataFlow.Network.Interfaces;

import java.util.Collection;
import java.util.List;

public interface Channel extends SignalNode {
    boolean isReady();

    void signalReady();


    SignalNode setSource(SignalNode channel);


    SignalNode addTarget(SignalNode channel);

    SignalNode getSource();

    Collection<SignalNode> getTargets();

    //boolean flip();
}
