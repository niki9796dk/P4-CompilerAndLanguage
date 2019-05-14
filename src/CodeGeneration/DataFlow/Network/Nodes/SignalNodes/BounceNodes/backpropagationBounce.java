package CodeGeneration.DataFlow.Network.Nodes.SignalNodes.BounceNodes;

import CodeGeneration.DataFlow.Network.Nodes.Block;
import CodeGeneration.DataFlow.Network.Nodes.SignalNodes.Channel;
import CodeGeneration.utility.Print;
import Enums.AnsiColor;
import LinearAlgebra.Types.Matrices.Matrix;
import MachineLearning.NeuralNetwork.ANN.ActivactionFunctions.ActivationFunction;
import MachineLearning.NeuralNetwork.Trainer.Costs.CostFunction;
import MachineLearning.NeuralNetwork.Trainer.Costs.MSECost;

public class backpropagationBounce extends AbstractBounceNode {
    private static Print print = new Print(AnsiColor.BLUE, "FeedForwardBouncer");

    private Channel source;
    private Matrix outputs;
    private Matrix derivatives;

    public backpropagationBounce(Matrix outputs) {
        this.outputs = outputs;
    }

    @Override
    public boolean isReadyBackpropagation() {
        return true;
    }

    @Override
    public void acceptReadySignal() {
        // Perform the required bounce logic
        print.say("Signal has reached the end.");

        // Rebounce
        this.acceptReadyBackpropagationSignal();
    }

    private void calculateDerivatives() {
        CostFunction costFunction = new MSECost();

        Matrix target = this.outputs;
        Matrix prediction = this.source.getResult();

        this.derivatives = costFunction.costPrime(target, prediction);
    }

    @Override
    public void acceptReadyBackpropagationSignal() {
        print.say("I have accepted the back prop signal.");


        // Calcualte the backprop signal
        this.calculateDerivatives();

        // Start the bounce
        this.source.acceptReadyBackpropagationSignal();
    }

    @Override
    public Matrix getResultBackpropagation(Channel channel) {
        if (this.derivatives == null) {
            throw new NullPointerException("The back prop result is null");
        }

        return this.derivatives;
    }

    @Override
    public void connectToMainBlock(Block mainBlock) {
        Channel[] mainBlockOutputs = mainBlock.getOutputChannels().values().toArray(new Channel[0]);

        if (mainBlockOutputs.length != 1) {
            throw new IllegalArgumentException("The main block MUST have only one input and output!");
        }

        Channel outputChannel = mainBlockOutputs[0];

        if (outputChannel == null) {
            throw new NullPointerException("The input channel was null");
        }

        this.source = outputChannel;     // Connect this to that
        outputChannel.addTarget(this);   // Connect that to this
    }
}
