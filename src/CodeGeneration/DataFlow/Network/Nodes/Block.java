package CodeGeneration.DataFlow.Network.Nodes;

import CodeGeneration.DataFlow.Network.Node;
import CodeGeneration.DataFlow.Network.Nodes.SignalNodes.Channel;
import LinearAlgebra.Types.Matrices.Matrix;

import java.util.Map;

public interface Block extends Node {
    boolean hasInputChannel(String id);

    boolean hasOutputChannel(String id);

    Block connectTo(Block toBlock, String fromChannel, String toChannel);

    Block connectTo(Channel channel);

    Block receiveGroupConnection(Node... nodes);

    Block receiveGroupConnection(Block... blocks);

    Block receiveGroupConnection(Channel... channels);

    Channel getChannel(String channelId);

    Matrix evaluateInput(Matrix inputData);

    void train(Matrix inputData, Matrix targetData, int iterations, double learningRate);

    void train(Matrix inputData, Matrix targetData, int iterations);

    Map<String, Channel> getInputChannels();

    Map<String, Channel> getOutputChannels();


    default Channel getFirstOutput() {
        return this.getOutputChannels().values().iterator().next();
    }

    default Channel getFirstInput() {
        return this.getInputChannels().values().iterator().next();
    }

}
