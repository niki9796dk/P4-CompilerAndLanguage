package CodeGeneration.DataFlow.Network.Nodes.SignalNodes.Channels;

import CodeGeneration.DataFlow.Network.Nodes.SignalNode;
import CodeGeneration.DataFlow.Network.Nodes.SignalNodes.Channel;
import LinearAlgebra.Types.Matrices.Matrix;
import LinearAlgebra.Types.Matrices.MatrixBuilder;

import javax.print.DocFlavor;
import java.util.*;

/**
 * A channel that uses list implementation (**!!!!!And not a list of channels!!!!!**)
 */
public class ListChannel implements Channel {
    private boolean ready = false;
    private boolean backpropagationReady = false;
    private SignalNode source;
    private List<SignalNode> targets = new ArrayList<>();

    /**
     * Constructor for ListChannel class that sets a source and a number of target SignalNodes
     *
     * @param source  The SignalNode that enters the channel
     * @param targets The list of SignalNodes that exit the channel and sends information
     */
    public ListChannel(SignalNode source, SignalNode... targets) {
        this.source = source;
        this.targets.addAll(Arrays.asList(targets));
    }

    public ListChannel() {
    }

    /**
     * Adds a channel as a target
     *
     * @param channel The target channel that this connects to
     * @return Returns itself to allow chaining method calls
     */
    public ListChannel addTarget(SignalNode channel) {
        this.targets.add(channel);
        return this;
    }

    /**
     * Gets the resulting matrix of data from the source
     *
     * @return A matrix of data
     */
    @Override
    public Matrix getResult() {
        Matrix result = this.source.getResult();

        if (result == null)
            throw new NullPointerException("Result is null!");

        return result;
    }

    @Override
    public Matrix getResultBackpropagation(Channel ignored) {
        LinkedList<SignalNode> signalNodes = new LinkedList<>(this.getTargets());

        if(signalNodes.isEmpty())
            throw new RuntimeException("No targets!");

        Matrix result = signalNodes.pollFirst().getResult();

        while (!signalNodes.isEmpty())
            result = result.add(signalNodes.pollFirst().getResult());

        return result;
    }

    /**
     * @return
     */
    @Override
    public boolean isReady() {
        return this.ready;
    }

    @Override
    public void sendReadySignals() {
        for (SignalNode target : targets)
            target.acceptReadySignal();
    }

    @Override
    public void acceptReadySignal() {
        this.ready = true;  // Store ready state for later recall
        this.sendReadySignals(); // Signal all outputs that their input is now ready.
    }

    @Override
    public void sendReadyBackpropagationSignals() {
        this.source.acceptReadyBackpropagationSignal();
    }

    @Override
    public void acceptReadyBackpropagationSignal() {
        for (SignalNode target : targets)
            if(!target.isReadyBackpropagation())
                return;

        this.backpropagationReady = true;
        this.sendReadyBackpropagationSignals();
    }

    @Override
    public boolean isReadyBackpropagation() {
        return this.backpropagationReady;
    }

    @Override
    public SignalNode getSource() {
        return source;
    }

    @Override
    public List<SignalNode> getTargets() {
        return new LinkedList<>(targets);
    }

    public ListChannel setSource(SignalNode channel) {

        if (channel == null)
            throw new NullPointerException("Channel is null!");

        this.source = channel;

        return this;
    }

    @Override
    public void tether(Channel that) {
        that.setSource(this);
        this.addTarget(that);
    }
}
