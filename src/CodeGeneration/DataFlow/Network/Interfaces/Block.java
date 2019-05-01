package CodeGeneration.DataFlow.Network.Interfaces;

import CodeGeneration.DataFlow.Network.ChannelId;

public interface Block extends Node {
    Block connectTo(Block toBlocks, ChannelId fromGate, ChannelId toGate);
    Channel getGate(ChannelId channelId);
}
