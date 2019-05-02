package CodeGeneration.DataFlow.Network;

import CodeGeneration.DataFlow.Network.Interfaces.Block;
import CodeGeneration.DataFlow.Network.Interfaces.Channel;
import CodeGeneration.utility.Print;
import Enums.AnsiColor;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractBlock implements Block {
    private Print print = new Print(AnsiColor.PURPLE, "Block." + this.getClass().getSimpleName());

    private Map<String, Channel> inputChannels = new HashMap<>(2);
    private Map<String, Channel> outputChannels = new HashMap<>(1);

    /**
     * Add an input channel
     *
     * @param id Desired id of the new channel.
     * @param c  The channel
     * @return a reference to this object.
     */
    public AbstractBlock addNewInputLabel(String id, Channel c) {
        print.say("New input: " + id);

        if(c == null)
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
        //print.say("New output: " + id);
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

        if(targetChannel == null)
            throw new NullPointerException("targetChannel is null!");

        ///////
        outputChannel.addTarget(targetChannel);

        targetChannel.setSource(outputChannel);

        ///////
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
}
