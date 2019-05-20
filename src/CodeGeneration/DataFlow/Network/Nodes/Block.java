package CodeGeneration.DataFlow.Network.Nodes;

import CodeGeneration.DataFlow.Network.Nodes.SignalNodes.Channel;
import CodeGeneration.DataFlow.Network.Node;

import java.util.Map;

public interface Block extends Node {
    boolean hasInputChannel(String id);

    boolean hasOutputChannel(String id);

    Block connectTo(Block toBlock, String fromChannel, String toChannel);
    Block connectTo(Channel channel);

    Block receiveGroupConnection(Block... blocks);

    Block receiveGroupConnection(Channel... channels);

    Channel getChannel(String channelId);

    Map<String, Channel> getInputChannels();
    Map<String, Channel> getOutputChannels();
}
