package CodeGeneration.DataFlow.Network.Nodes.SignalNodes.Channels;

import CodeGeneration.DataFlow.Network.Nodes.SignalNode;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * A channel that uses list implementation (**!!!!!And not a list of channels!!!!!**)
 */
public class ListChannelSource extends AbstractChannel {

    /**
     * Constructor for ListChannel class that sets a source and a number of target SignalNodes
     *
     * @param source  The SignalNode that enters the channel
     * @param targets The list of SignalNodes that exit the channel and sends information
     */
    public ListChannelSource(SignalNode source, SignalNode... targets) {
        super(new LinkedList<>(), source, targets);
        this.targets = new ArrayList<>();
    }

    public ListChannelSource() {
        super(new LinkedList<>());
    }

    @Override
    public boolean isReady() {
        forward.say("I was asked if i was ready, which i ofcouse was");
        return true;
    }

    @Override
    public boolean isSource() {
        forward.say("I was asked if i was a source connection, which i ofcouse was");
        return true;
    }

    @Override
    public void acceptReadySignal() {
        forward.say("I as a source channel, accepted a ready signal");
        super.acceptReadySignal();
    }

    @Override
    public void acceptReadyBackpropagationSignal() {
        backprop.say("I as a source channel, accepted a back prop signal");
        super.acceptReadyBackpropagationSignal();
    }
}
