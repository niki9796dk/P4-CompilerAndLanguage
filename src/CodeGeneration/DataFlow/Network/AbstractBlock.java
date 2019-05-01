package CodeGeneration.DataFlow.Network;

import CodeGeneration.DataFlow.Network.Interfaces.Block;
import CodeGeneration.DataFlow.Network.Interfaces.Channel;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractBlock implements Block {
    private Map<ChannelId, Channel> inputGates = new HashMap<>(2);
    private Map<ChannelId, Channel> outputGates = new HashMap<>(1);


    /**
     * Add an input gate
     *
     * @param id Desired id of the new gate.
     * @param g  The gate
     * @return a reference to this object.
     */
    public AbstractBlock addInput(ChannelId id, Channel g) {
        this.inputGates.put(id, g);
        return this;
    }

    /**
     * Add an output gate
     *
     * @param id Desired id of the new gate.
     * @param g  The gate
     * @return a reference to this object.
     */
    public AbstractBlock addOutput(ChannelId id, Channel g) {
        this.outputGates.put(id, g);
        return this;
    }

    /**
     * Get all output gates.
     *
     * @return all output gates.
     */
    public Collection<Channel> getOutputs() {
        return this.outputGates.values();
    }

    /**
     * Get all input gates.
     *
     * @return all input gates.
     */
    public Collection<Channel> getInputs() {
        return this.inputGates.values();
    }

    @Override
    public Block connectTo(Block toBlock, ChannelId fromGate, ChannelId toGate) {
        Channel outputGate =
                this.outputGates.getOrDefault(fromGate,
                        this.inputGates.getOrDefault(fromGate, null)
                );

        if (outputGate == null)
            throw new NullPointerException();

        Channel targetGate = toBlock.getGate(toGate); // TODO: Null check?

        outputGate.addTarget(targetGate);
        targetGate.setSource(outputGate);

        return this;
    }

    @Override
    public Channel getGate(ChannelId channelId) {
        if (this.inputGates.containsKey(channelId)) {
            return this.inputGates.get(channelId);
        } else if (this.outputGates.containsKey(channelId)) {
            return this.outputGates.get(channelId);
        } else {
            throw new IllegalArgumentException("No such gate: " + channelId);
        }
    }
}
