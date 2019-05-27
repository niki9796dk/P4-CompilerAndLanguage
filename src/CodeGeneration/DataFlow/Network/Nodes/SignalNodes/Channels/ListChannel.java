package CodeGeneration.DataFlow.Network.Nodes.SignalNodes.Channels;

import CodeGeneration.DataFlow.Network.Nodes.SignalNode;

import java.util.ArrayList;

/**
 * A channel that uses list implementation (**!!!!!And not a list of channels!!!!!**)
 */
public class ListChannel extends AbstractChannel {

    /**
     * Constructor for ListChannel class that sets a source and a number of target SignalNodes
     *
     * @param source  The SignalNode that enters the channel
     * @param targets The list of SignalNodes that exit the channel and sends information
     */
    public ListChannel(SignalNode source, SignalNode... targets) {
        super(new ArrayList<>(), source, targets);
    }

    public ListChannel() {
        super(new ArrayList<>());
    }

}
