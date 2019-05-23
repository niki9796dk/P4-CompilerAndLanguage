package CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.UnaryOperations;

import CodeGeneration.DataFlow.Network.Nodes.Blocks.AbstractBlock;
import CodeGeneration.DataFlow.Network.Nodes.SignalNodes.Channel;
import CodeGeneration.DataFlow.Network.Nodes.SignalNodes.Channels.ListChannel;
import CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.AbstractOperation;
import LinearAlgebra.Types.Matrices.Matrix;
import LinearAlgebra.Types.Matrices.MatrixBuilder;
import MachineLearning.NeuralNetwork.ANN.ActivactionFunctions.ActivationFunction;
import TypeChecker.Exceptions.ShouldNotHappenException;

import java.util.HashMap;

public abstract class UnaryAbstractOperation extends AbstractOperation {
    public static final String UNARY_IN_CHANNEL = "A";
    public static final String OPERATION_OUT_CHANNEL = "out";

    protected UnaryAbstractOperation() {
        // Define inputs
        Channel in = new ListChannel();
        in.addTarget(this);

        // Define outputs
        Channel out = new ListChannel(this);

        // Store channels
        this
                .addNewInputLabel(UNARY_IN_CHANNEL, in)
                .addNewOutputLabel(OPERATION_OUT_CHANNEL, out);
    }

    @Override
    public void performOperation() {
        Matrix in = this.getInputValue(UNARY_IN_CHANNEL);

        if (in == null)
            throw new NullPointerException("in is null!");

        this.result = operation(in);
    }

    @Override
    public Channel getOutputChannel() {
        return this.getChannel(OPERATION_OUT_CHANNEL);
    }

    protected abstract Matrix operation(Matrix in);

    @Override
    public void performBackpropagationOperation() {
        Matrix in = this.getInputValue(UNARY_IN_CHANNEL);
        Matrix out = this.getOutputChannel().getResult();

        if (in == null)
            throw new NullPointerException(UNARY_IN_CHANNEL + " is null!");

        if (out == null)
            throw new NullPointerException("out is null!");

        this.resultBackpropagation = this.operationBackpropagation(
                this.getChannel(UNARY_IN_CHANNEL),
                this.getChannel(OPERATION_OUT_CHANNEL)
        );
    }

    protected abstract HashMap<Channel, Matrix> operationBackpropagation(Channel in, Channel out);

    @Override
    public AbstractBlock addNewInputLabel(String id, Channel c) {
        if (!id.equals(UNARY_IN_CHANNEL))
            throw new IllegalArgumentException("Input channel has to be in for UnaryOperation objects!");
        return super.addNewInputLabel(id, c);
    }
}
