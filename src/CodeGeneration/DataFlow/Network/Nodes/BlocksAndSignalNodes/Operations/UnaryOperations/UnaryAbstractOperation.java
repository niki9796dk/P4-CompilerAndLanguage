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
        print.say("performOperation() -> result = " + this.result);
    }

    @Override
    public Channel getOutputChannel() {
        return this.getChannel("out");
    }

    protected Matrix operation(Matrix in) {
        return this.getFunction().activation(in);
    }

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

    protected HashMap<Channel, Matrix> operationBackpropagation(Channel in, Channel out) {
        HashMap<Channel, Matrix> backpropResults = new HashMap<>();

        backpropResults.put(in, this.calculateInDerivatives(in.getResult(), out.getResultBackpropagation()));

        return backpropResults;
    }

    protected Matrix calculateInDerivatives(Matrix in, Matrix out) {
        return out.compMult(this.getAfDeri(in, this.result));
    }

    private Matrix getAfDeri(Matrix net, Matrix out) {
        switch (this.getFunction().getMatrixPref()) {
            case NET:
                return this.getFunction().activationPrime(net);

            case OUT:
                return this.getFunction().activationPrime(out);

            default:
                throw new ShouldNotHappenException("No such matrix pref?");
        }
    }

    protected abstract ActivationFunction getFunction();

    @Override
    public AbstractBlock addNewInputLabel(String id, Channel c) {
        if (!id.equals("in"))
            throw new IllegalArgumentException("Input channel has to be in for UnaryOperation objects!");
        return super.addNewInputLabel(id, c);
    }
}
