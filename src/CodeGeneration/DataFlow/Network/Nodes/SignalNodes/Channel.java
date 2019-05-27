package CodeGeneration.DataFlow.Network.Nodes.SignalNodes;

import CodeGeneration.DataFlow.Network.Nodes.SignalNode;
import LinearAlgebra.Types.Matrices.Matrix;

import java.util.Collection;

public interface Channel extends SignalNode {

    boolean isReady();

    void sendReadySignals();

    void sendReadyBackpropagationSignals();

    SignalNode setSource(SignalNode channel);

    SignalNode addTarget(SignalNode channel);

    SignalNode getSource();

    void tether(Channel that);

    Collection<SignalNode> getTargets();

    default Matrix getResultBackpropagation() {
        return this.getResultBackpropagation(null);
    }

    void clearSource();

    void clearTargets();
}
