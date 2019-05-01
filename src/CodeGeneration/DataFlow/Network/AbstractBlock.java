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
    public AbstractBlock addInput(String id, Channel c) {
        print.say("New input: " + id);
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
    public AbstractBlock addOutput(String id, Channel c) {
        print.say("New output: " + id);
        this.outputChannels.put(id, c);
        return this;
    }

    /**
     * Checks if this block has input channel with id
     * @param id the identifier to check for
     * @return whether this block has input channel with id
     */
    @Override
    public boolean hasInputChannel(String id){
        return this.inputChannels.containsKey(id);
    }

    /**
     * Checks if this block has output channel with id
     * @param id the identifier to check for
     * @return whether this block has output channel with id
     */
    @Override
    public boolean hasOutputChannel(String id){
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
     * Connect all channels with the same id
     * @param that the block to connect to.
     * @return a reference to this object.
     */
    @Override
    public Block connectAll(Block that) {
        for(String id: this.outputChannels.keySet()){
            if(that.hasInputChannel(id))
                this.connectTo(that, id);
        }
        return this;
    }

    /**
     * Connect this block to toBlock via channels with identical ids
      * @param toBlocks the block to connect to
     * @param channelIds the id of both the fromChannel and toChannel
     * @return a reference to this object.
     */
    @Override
    public Block connectTo(Block toBlocks, String channelIds) {
        return this.connectTo(toBlocks, channelIds, channelIds);
    }

    /**
     * Connect this block to toBlock via fromChannel to toChannel
     * @param toBlock the block to connect to
     * @param fromChannelId the output channel id from this where connection starts
     * @param toChannelId the input channel id from toBlock where connection ends
     * @return a reference to this object.
     */
    @Override
    public Block connectTo(Block toBlock, String fromChannelId, String toChannelId) {

        Channel outputChannel =
                this.outputChannels.getOrDefault(fromChannelId,
                        this.inputChannels.get(fromChannelId)
                );

        if (outputChannel == null) {
            throw new NullPointerException();
        }

        Channel targetChannel = toBlock.getChannel(toChannelId);

        outputChannel.addTarget(targetChannel);
        targetChannel.setSource(outputChannel);

        return this;
    }


    /**
     * Get channel with given id
     * @param channelId id of channel
     * @return a reference to this channel
     */
    @Override
    public Channel getChannel(String channelId) {
        if (this.inputChannels.containsKey(channelId)) {
            return this.inputChannels.get(channelId);

        } else if (this.outputChannels.containsKey(channelId)) {
            return this.outputChannels.get(channelId);

        } else {
            throw new IllegalArgumentException("No such channel: " + channelId);
        }
    }
}
