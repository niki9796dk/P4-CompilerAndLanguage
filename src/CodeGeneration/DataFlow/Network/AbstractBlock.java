package CodeGeneration.DataFlow.Network;

import CodeGeneration.DataFlow.Network.Interfaces.Block;
import CodeGeneration.DataFlow.Network.Interfaces.Channel;
import CodeGeneration.utility.Print;
import Enums.AnsiColor;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractBlock implements Block {
    private Print print = new Print(AnsiColor.PURPLE, this.getClass().getSimpleName());

    private Map<String, Channel> inputChannels = new HashMap<>(2);
    private Map<String, Channel> outputChannels = new HashMap<>(1);


    /**
     * Add an input channel
     *
     * @param id Desired id of the new channel.
     * @param c  The channel
     * @return a reference to this object.
     */
    public AbstractBlock addInput(ChannelId id, Channel c) {
        return this.addInput(id.name(), c);
    }

    public AbstractBlock addInput(String id, Channel c) {
        print.say("New input: " + id);
        this.inputChannels.put(id, c);
        return this;
    }

    /**
     * Add an output channel
     *
     * @param id Desired id of the new channel.
     * @param c  The channel
     * @return a reference to this object.
     */
    public AbstractBlock addOutput(ChannelId id, Channel c) {
        return this.addOutput(id.name(), c);
    }

    public AbstractBlock addOutput(String id, Channel g) {
        print.say("New output: " + id);
        this.outputChannels.put(id, g);
        return this;
    }


        /**
         * Get all output channels.
         *
         * @return all output channels.
         */
        public Collection<Channel> getOutputs () {
            return this.outputChannels.values();
        }

        /**
         * Get all input channels.
         *
         * @return all input channels.
         */
        public Collection<Channel> getInputs () {
            return this.inputChannels.values();
        }

    @Override
    public Block connectTo(Block toBlocks, ChannelId fromChannel, ChannelId toChannel) {
        return this.connectTo(toBlocks, fromChannel.name(), toChannel);
    }

    @Override
        public Block connectTo(Block toBlock, String fromChannel, ChannelId toChannel){
            Channel outputChannel =
                    this.outputChannels.getOrDefault(fromChannel,
                            this.inputChannels.getOrDefault(fromChannel, null)
                    );

            if (outputChannel == null) {
                throw new NullPointerException();
            }

            Channel targetChannel = toBlock.getChannel(toChannel);

            outputChannel.addTarget(targetChannel);
            targetChannel.setSource(outputChannel);

            return this;
        }

        @Override
        public Channel getChannel(ChannelId channelId){
            return this.getChannel(channelId.name());
        }

        @Override
        public Channel getChannel (String channelId){
            if (this.inputChannels.containsKey(channelId)) {
                return this.inputChannels.get(channelId);

            } else if (this.outputChannels.containsKey(channelId)) {
                return this.outputChannels.get(channelId);

            } else {
                throw new IllegalArgumentException("No such channel: " + channelId);
            }
        }
    }
