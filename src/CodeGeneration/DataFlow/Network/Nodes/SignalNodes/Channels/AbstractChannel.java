package CodeGeneration.DataFlow.Network.Nodes.SignalNodes.Channels;

import CodeGeneration.DataFlow.Network.Nodes.SignalNode;
import CodeGeneration.DataFlow.Network.Nodes.SignalNodes.Channel;
import LinearAlgebra.Types.Matrices.Matrix;

import java.util.*;

public abstract class AbstractChannel implements Channel {
    protected boolean ready = false;
    protected boolean backpropagationReady = false;
    protected SignalNode source;
    protected Collection<SignalNode> targets;


    /**
     * Constructor for ListChannel class that sets a source and a number of target SignalNodes
     *
     * @param source  The SignalNode that enters the channel
     * @param targets The list of SignalNodes that exit the channel and sends information
     */
    public AbstractChannel(Collection<SignalNode> targetsCollection, SignalNode source, SignalNode... targets) {
        this(targetsCollection);
        this.source = source;
        this.targets.addAll(Arrays.asList(targets));
    }

    public AbstractChannel(Collection<SignalNode> targets) {
        this.targets = targets;
    }

    /**
     * Adds a channel as a target
     *
     * @param channel The target channel that this connects to
     * @return Returns itself to allow chaining method calls
     */
    public AbstractChannel addTarget(SignalNode channel) {
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

        if (result == null) {
            throw new NullPointerException("Result is null!");
        }

        return result;
    }

    @Override
    public Matrix getResultBackpropagation(Channel ignored) {
        LinkedList<SignalNode> signalNodes = new LinkedList<>(this.getTargets());

        if(signalNodes.isEmpty()) {
            throw new RuntimeException("No targets!");
        }

        Matrix result = signalNodes.pollFirst().getResultBackpropagation(this);

        while (!signalNodes.isEmpty()) {
            result = result.add(signalNodes.pollFirst().getResultBackpropagation(this));
        }

        return result;
    }

    /**
     * @return
     */
    @Override
    public boolean isReady() {
        if (this.ready) {
            return true;
        }

        return (this.ready = this.isSource());
    }

    @Override
    public boolean isSource() {
        if (this.source instanceof Channel) {
            return (this.ready = ((Channel) this.source).isReady());

        } else {
            return false;
        }
    }

    @Override
    public void sendReadySignals() {
        this.backpropagationReady = false;

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
        this.ready = false;
        this.source.acceptReadyBackpropagationSignal();
    }

    @Override
    public void acceptReadyBackpropagationSignal() {

        for (SignalNode target : targets) {
            if(!target.isReadyBackpropagation()) {
                return;
            }
        }

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

    public AbstractChannel setSource(SignalNode channel) {

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

    @Override
    public void clearSource() {
        this.source = null;
    }

    @Override
    public void clearTargets() {
        this.targets.clear();
    }
}
