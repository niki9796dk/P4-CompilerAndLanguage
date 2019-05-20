package CodeGeneration.DataFlow.Network.Nodes.Blocks;

import CodeGeneration.DataFlow.Network.Node;
import CodeGeneration.DataFlow.Network.Nodes.Block;
import CodeGeneration.DataFlow.Network.Nodes.SignalNodes.Channel;
import CodeGeneration.utility.Print;
import Enums.AnsiColor;

import java.util.*;

public abstract class AbstractBlock implements Block {
    private Print print = new Print(AnsiColor.PURPLE, "Block." + this.getClass().getSimpleName());

    private LinkedHashMap<String, Channel> inputChannels = new LinkedHashMap<>(2);
    private LinkedHashMap<String, Channel> outputChannels = new LinkedHashMap<>(1);

    /**
     * Add an input channel
     *
     * @param id Desired id of the new channel.
     * @param c  The channel
     * @return a reference to this object.
     */
    public AbstractBlock addNewInputLabel(String id, Channel c) {
        //forward.say("New input: " + id);

        if (c == null)
            throw new NullPointerException("input c is null!");

        this.inputChannels.put(id, c);
        return this;
    }

    /**
     * Add an input channel
     *
     * @param id Desired id of the new channel.
     * @param c  The channel
     * @return a reference to this object.
     */
    public AbstractBlock addNewOutputLabel(String id, Channel c) {
        //forward.say("New output: " + id);
        this.outputChannels.put(id, c);
        return this;
    }

    /**
     * Checks if this block has input channel with id
     *
     * @param id the identifier to check for
     * @return whether this block has input channel with id
     */
    @Override
    public boolean hasInputChannel(String id) {
        return this.inputChannels.containsKey(id);
    }

    /**
     * Checks if this block has output channel with id
     *
     * @param id the identifier to check for
     * @return whether this block has output channel with id
     */
    @Override
    public boolean hasOutputChannel(String id) {
        return this.outputChannels.containsKey(id);
    }


    /**
     * Get all output channels.
     *
     * @return all output channels.
     */
    public Collection<Channel> getOutputs() {
        return this.outputChannels.values();
    }

    /**
     * Get all input channels.
     *
     * @return all input channels.
     */
    public Collection<Channel> getInputs() {
        return this.inputChannels.values();
    }

    /**
     * Connect this block to toBlock via fromChannel to toChannel
     *
     * @param toBlock       the block to connect to
     * @param fromChannelId the output channel id from this where connection starts
     * @param toChannelId   the input channel id from toBlock where connection ends
     * @return a reference to this object.
     */
    @Override
    public Block connectTo(Block toBlock, String fromChannelId, String toChannelId) {

        ///////
        Channel outputChannel;

        outputChannel = this.outputChannels.get(fromChannelId);
        if (outputChannel == null)
            outputChannel = this.inputChannels.get(fromChannelId);

        if (outputChannel == null)
            throw new NullPointerException("outputChannel is null!");

        ///////
        Channel targetChannel = toBlock.getChannel(toChannelId);

        if (targetChannel == null)
            throw new NullPointerException("targetChannel is null!");

        ///////
        outputChannel.tether(targetChannel);

        ///////
        // Allow back propagation //


        ///////
        return this;
    }

    @Override
    public Block receiveGroupConnection(Node... nodes) {
        LinkedList<Channel> inputKeys = new LinkedList<>(this.inputChannels.values());

        if (inputKeys.size() != nodes.length)
            throw new IllegalArgumentException("The amount of group connections MUST match the amount of inputs.");

        for (Node node : nodes){
            Channel channel = null;

            if(node instanceof Channel) channel = (Channel) node;
            else if(node instanceof Block) ((Block) node).getFirstOutput()
            else throw new IllegalArgumentException("Input must be a block or channel in the current implementation");
            assert channel != null;

            channel.tether(inputKeys.pollFirst());
        }
        return this;
    }

    @Override
    public Block receiveGroupConnection(Block... blocks) {
        for (Block block : blocks)
            if (block.getOutputChannels().size() != 1)
                throw new IllegalArgumentException("The amount of outputs in every input block MUST be 1!");

        Channel[] channels = new Channel[blocks.length];

        int i = 0;
        for(Block block: blocks)
            channels[i++] = block.getOutputChannels().values().iterator().next();

        return this.receiveGroupConnection(channels);
    }

    @Override
    public Block receiveGroupConnection(Channel... channels) {
        LinkedList<String> inputKeys = new LinkedList<>(this.inputChannels.keySet());

        if (inputKeys.size() != channels.length)
            throw new IllegalArgumentException("The amount of group connections MUST match the amount of inputs.");

        for (Channel channel : channels)
            channel.tether(this.inputChannels.get(inputKeys.pollFirst()));

        return this;
    }

    /**
     * Get channel with given id
     *
     * @param channelId id of channel
     * @return a reference to this channel
     */
    @Override
    public Channel getChannel(String channelId) {
        Channel out;

        out = this.inputChannels.get(channelId);
        if (out != null) return out;

        out = this.outputChannels.get(channelId);
        if (out != null) return out;

        throw new IllegalArgumentException("No such channel: " + channelId);

    }

    @Override
    public Map<String, Channel> getInputChannels() {
        return this.inputChannels;
    }

    @Override
    public Map<String, Channel> getOutputChannels() {
        return this.outputChannels;
    }
}
