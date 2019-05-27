package CodeGeneration.DataFlow.Network.Nodes.Blocks;

import CodeGeneration.DataFlow.Network.Node;
import CodeGeneration.DataFlow.Network.Nodes.Block;
import CodeGeneration.DataFlow.Network.Nodes.SignalNodes.BounceNode;
import CodeGeneration.DataFlow.Network.Nodes.SignalNodes.BounceNodes.FeedforwardBounce;
import CodeGeneration.DataFlow.Network.Nodes.SignalNodes.BounceNodes.backpropagationBounce;
import CodeGeneration.DataFlow.Network.Nodes.SignalNodes.Channel;
import LinearAlgebra.Types.Matrices.Matrix;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

public abstract class AbstractBlock implements Block {

    // Fields:
    private LinkedHashMap<String, Channel> inputChannels = new LinkedHashMap<>(2);
    private LinkedHashMap<String, Channel> outputChannels = new LinkedHashMap<>(1);
    public static BlockConfiguration configuration;

    // Constants:
    private static final double DEFAULT_LEARNING_RATE = 0.2;

    /**
     * Add an input channel
     *
     * @param id Desired id of the new channel.
     * @param c  The channel
     * @return a reference to this object.
     */
    public AbstractBlock addNewInputLabel(String id, Channel c) {
        //forward.say("New input: " + id);

        if (c == null)
            throw new NullPointerException("input c is null!");

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
    public AbstractBlock addNewOutputLabel(String id, Channel c) {
        //forward.say("New output: " + id);
        this.outputChannels.put(id, c);
        return this;
    }

    /**
     * Checks if this block has input channel with id
     *
     * @param id the identifier to check for
     * @return whether this block has input channel with id
     */
    @Override
    public boolean hasInputChannel(String id) {
        return this.inputChannels.containsKey(id);
    }

    /**
     * Checks if this block has output channel with id
     *
     * @param id the identifier to check for
     * @return whether this block has output channel with id
     */
    @Override
    public boolean hasOutputChannel(String id) {
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
     * Connect this block to toBlock via fromChannel to toChannel
     *
     * @param toBlock       the block to connect to
     * @param fromChannelId the output channel id from this where connection starts
     * @param toChannelId   the input channel id from toBlock where connection ends
     * @return a reference to this object.
     */
    @Override
    public Block connectTo(Block toBlock, String fromChannelId, String toChannelId) {

        ///////
        Channel outputChannel = this.getChannel(fromChannelId);

        if (outputChannel == null) {
            throw new NullPointerException("outputChannel is null!");
        }

        ///////
        Channel targetChannel = toBlock.getChannel(toChannelId);

        if (targetChannel == null) {
            throw new NullPointerException("targetChannel is null!");
        }

        ///////
        outputChannel.tether(targetChannel);

        ///////
        return this;
    }

    @Override
    public Block connectTo(Channel targetChannel) {
        if (this.outputChannels.values().size() != 1) {
            throw new RuntimeException("The connectTo(Channel) can only be used, if the block has a total of 1 output channel");
        }

        Channel myOutChannel = this.getOutputChannels().values().iterator().next();

        myOutChannel.tether(targetChannel);

        return this;
    }

    @Override
    public Block receiveGroupConnection(Node... nodes) {
        LinkedList<Channel> inputKeys = new LinkedList<>(this.inputChannels.values());

        if (inputKeys.size() != nodes.length)
            throw new IllegalArgumentException("The amount of group connections MUST match the amount of inputs.");

        for (Node node : nodes) {
            Channel channel;

            if (node instanceof Channel) channel = (Channel) node;
            else if (node instanceof Block) channel = ((Block) node).getFirstOutput();
            else throw new IllegalArgumentException("Input must be a block or channel in the current implementation");

            channel.tether(inputKeys.pollFirst());
        }
        return this;
    }

    @Override
    public Block receiveGroupConnection(Block... blocks) {
        for (Block block : blocks)
            if (block.getOutputChannels().size() != 1)
                throw new IllegalArgumentException("The amount of outputs in every input block MUST be 1!");

        Channel[] channels = new Channel[blocks.length];

        int i = 0;
        for (Block block : blocks)
            channels[i++] = block.getOutputChannels().values().iterator().next();

        return this.receiveGroupConnection(channels);
    }

    @Override
    public Block receiveGroupConnection(Channel... channels) {
        LinkedList<String> inputKeys = new LinkedList<>(this.inputChannels.keySet());

        if (inputKeys.size() != channels.length)
            throw new IllegalArgumentException("The amount of group connections MUST match the amount of inputs.");

        for (Channel channel : channels) {
            channel.tether(this.inputChannels.get(inputKeys.pollFirst()));
        }

        return this;
    }

    /**
     * Get channel with given id
     *
     * @param channelId id of channel
     * @return a reference to this channel
     */
    @Override
    public Channel getChannel(String channelId) {
        Channel out;

        out = this.inputChannels.get(channelId);
        if (out != null) return out;

        out = this.outputChannels.get(channelId);
        if (out != null) return out;

        throw new IllegalArgumentException("No such channel: " + channelId);

    }

    @Override
    public Map<String, Channel> getInputChannels() {
        return this.inputChannels;
    }

    @Override
    public Map<String, Channel> getOutputChannels() {
        return this.outputChannels;
    }

    @Override
    public Matrix evaluateInput(Matrix inputData) {
        if (this.getOutputChannels().size() != 1) {
            throw new IllegalCallerException("This method can only be called on blocks with one input and output");
        }

        BounceNode feedForward = new FeedforwardBounce(inputData);
        feedForward.connectToMainBlock(this);
        feedForward.acceptReadySignal();
        feedForward.releaseFromMainBlock();

        return this.getFirstOutput().getResult();
    }

    @Override
    public void train(Matrix inputData, Matrix targetData, int iterations, double learningRate) {
        AbstractBlock.configuration = new BlockConfiguration(inputData.getRows(), iterations);

        FeedforwardBounce feedForward = new FeedforwardBounce(inputData);
        backpropagationBounce backProp = new backpropagationBounce(targetData);

        feedForward.connectToMainBlock(this);
        backProp.connectToMainBlock(this);

        for (int i = 0; i < iterations; i++) {
            // Send the feedforward signal
            feedForward.acceptReadySignal();

            // Check if there were sufficient flow - Throw an exception if not.
            this.verifySignalFlow(feedForward, backProp);
        }

        feedForward.releaseFromMainBlock();
        backProp.releaseFromMainBlock();

        AbstractBlock.configuration = null;
    }

    private void verifySignalFlow(FeedforwardBounce feedForward, backpropagationBounce backProp) {
        boolean signalReachTheEnd = backProp.hasBeenTouched();
        boolean signalReturnedToStart = feedForward.hasBeenTouched();
        int backPropTouches = backProp.getTotalTouches();
        int feedforwardTouches = feedForward.getTotalTouches();

        if (!(signalReachTheEnd && signalReturnedToStart)) {
            String errorMsg;
            if (signalReachTheEnd) {
                errorMsg = "The signal could only reach the end, but not return to the start!?!?";
            } else if (signalReturnedToStart) {
                errorMsg = "The signal somehow returned back home, but could not reach the end!?!?";
            } else {
                errorMsg = "The signal died mid way!?!?";
            }

            throw new RuntimeException("There is not sufficient flow! - " + errorMsg);

        } else if (backPropTouches != 1 || feedforwardTouches != 1) {
            throw new RuntimeException("There was flow though the whole network... But the ends were touched a few times too many: FF[" + feedforwardTouches + "] and BP[" + backPropTouches + "]... They should both be 1.");
        }

        // Let the bouncers forget they were touched, to allow for future similar events.
        feedForward.forgetTouch();
        backProp.forgetTouch();
    }

    @Override
    public void train(Matrix inputData, Matrix targetData, int iterations) {
        this.train(inputData, targetData, iterations, DEFAULT_LEARNING_RATE);
    }
}
