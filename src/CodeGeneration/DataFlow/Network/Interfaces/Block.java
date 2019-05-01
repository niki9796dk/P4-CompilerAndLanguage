package CodeGeneration.DataFlow.Network.Interfaces;

import CodeGeneration.DataFlow.Network.ChannelId;

public interface Block extends Node {
    Block connectTo(Block toBlocks, ChannelId fromChannel, ChannelId toChannel);

    Block connectTo(Block toBlock, String fromChannel, ChannelId toChannel);

    Channel getChannel(ChannelId channelId);

    Channel getChannel(String channelId);
}
