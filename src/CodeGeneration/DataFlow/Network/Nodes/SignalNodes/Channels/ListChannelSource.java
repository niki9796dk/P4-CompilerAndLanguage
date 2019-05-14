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
        return true;
    }
}
