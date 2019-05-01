package CodeGeneration.DataFlow.Network;

import CodeGeneration.DataFlow.Network.Interfaces.Channel;
import CodeGeneration.DataFlow.Network.Interfaces.SignalNode;
import LinearAlgebra.Types.Matrices.Matrix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A channel that uses list implementation (**!!!!!And not a list of channels!!!!!**)
 */
public class ListChannel implements Channel {
    private boolean ready = false;
    private SignalNode source;
    private List<SignalNode> targets = new ArrayList<>();

    /**
     * Constructor for ListChannel class that sets a source and a number of target SignalNodes
     * @param source The SignalNode that enters the channel
     * @param targets
     */
    public ListChannel(SignalNode source, SignalNode ... targets) {
        this.source = source;
        this.targets.addAll(Arrays.asList(targets));
    }

    public ListChannel() {
    }

    @Deprecated
    public void setSource(SignalNode channel){
        this.source = channel;
    }

    public void addTarget(SignalNode channel){
        this.targets.add(channel);
    }

    @Override
    public Matrix getResult() {
        return this.source.getResult();
    }

    @Override
    public boolean isReady() {
        return this.ready;
    }

    @Override
    public void signalReady() {
        for (SignalNode target : this.targets) {
            target.acceptReadySignal();
        }
    }

    @Override
    public void acceptReadySignal() {
        this.ready = true;  // Store ready state for later recall
        this.signalReady(); // Signal all outputs that their input is now ready.
    }
}
