package CodeGeneration.DataFlow.Network.Interfaces;

import CodeGeneration.DataFlow.Network.ChannelId;

public interface Block extends Node {
    boolean hasInput(String id);

    boolean hasOutput(String id);

    Block connectAll(Block toBlocks);

    @Deprecated
    Block connectTo(Block toBlocks, ChannelId fromChannel, ChannelId toChannel);

    Block connectTo(Block toBlocks, String channelIds);

    Block connectTo(Block toBlock, String fromChannel, String toChannel);

    @Deprecated
    Channel getChannel(ChannelId channelId);

    Channel getChannel(String channelId);
}
