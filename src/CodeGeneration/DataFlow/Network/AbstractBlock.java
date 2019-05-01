package CodeGeneration.DataFlow.Network;

import CodeGeneration.DataFlow.Network.Interfaces.Block;
import CodeGeneration.DataFlow.Network.Interfaces.Channel;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractBlock implements Block {
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
        return this.addInput(id.name(), c);
    }

    public AbstractBlock addOutput(String id, Channel g) {
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
        public Block connectTo (Block toBlock, ChannelId fromChannel, ChannelId toChannel){
            Channel outputChannel =
                    this.outputChannels.getOrDefault(fromChannel,
                            this.inputChannels.getOrDefault(fromChannel, null)
                    );

            if (outputChannel == null)
                throw new NullPointerException();

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
