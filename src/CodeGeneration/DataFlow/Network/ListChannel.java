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
     *
     * @param source  The SignalNode that enters the channel
     * @param targets The list of SignalNodes exits the channel and sends information
     */
    public ListChannel(SignalNode source, SignalNode... targets) {
        this.source = source;
        this.targets.addAll(Arrays.asList(targets));
    }

    public ListChannel() {
    }

    public ListChannel addTarget(SignalNode channel) {
        this.targets.add(channel);
        return this;
    }

    @Override
    public Matrix getResult() {
        Matrix result = this.source.getResult();

        if (result == null)
            throw new NullPointerException("Result is null!");

        return result;
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

    @Override
    public SignalNode getSource() {
        return source;
    }

    public ListChannel setSource(SignalNode channel) {

        if (channel == null)
            throw new NullPointerException("Channel is null!");

        this.source = channel;

        if (channel.hasNoInput() && false)
            this.acceptReadySignal();

        return this;
    }
}
