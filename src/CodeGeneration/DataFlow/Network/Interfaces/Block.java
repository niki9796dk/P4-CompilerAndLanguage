package CodeGeneration.DataFlow.Network.Interfaces;

public interface Block extends Node {
    boolean hasInputChannel(String id);

    boolean hasOutputChannel(String id);

    Block connectAll(Block toBlocks);

    Block connectTo(Block toBlocks, String channelIds);

    Block connectTo(Block toBlock, String fromChannel, String toChannel);

    Channel getChannel(String channelId);
}
