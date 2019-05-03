package CodeGeneration.DataFlow.Network.Interfaces;

public interface Block extends Node {
    boolean hasInputChannel(String id);

    boolean hasOutputChannel(String id);

    Block connectTo(Block toBlock, String fromChannel, String toChannel);

    Channel getChannel(String channelId);

    //void flip();
}
