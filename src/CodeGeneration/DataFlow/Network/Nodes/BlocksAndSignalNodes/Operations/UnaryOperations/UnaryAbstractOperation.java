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
    protected UnaryAbstractOperation() {
        // Define inputs
        Channel in = new ListChannel();
        in.addTarget(this);

        // Define outputs
        Channel out = new ListChannel(this);

        // Store channels
        this
                .addNewInputLabel("in", in)
                .addNewOutputLabel("out", out);
    }

    @Override
    public void performOperation() {
        Matrix in = this.getInputValue("in");

        if (in == null)
            throw new NullPointerException("in is null!");

        this.result = operation(in);
    }

    @Override
    public Channel getOutputChannel() {
        return this.getChannel("out");
    }

    protected abstract Matrix operation(Matrix in);

    @Override
    public void performBackpropagationOperation() {
        Matrix in1 = this.getInputValue("in");
        Matrix out = this.getOutputChannel().getResult();

        if (in1 == null)
            throw new NullPointerException("in1 is null!");

        if (out == null)
            throw new NullPointerException("out is null!");

        this.resultBackpropagation = this.operationBackpropagation(
                this.getChannel("in"),
                this.getChannel("out")
        );
    }

    protected abstract HashMap<Channel, Matrix> operationBackpropagation(Channel in, Channel out);

    @Override
    public AbstractBlock addNewInputLabel(String id, Channel c) {
        if (!id.equals("in"))
            throw new IllegalArgumentException("Input channel has to be in for UnaryOperation objects!");
        return super.addNewInputLabel(id, c);
    }
}
