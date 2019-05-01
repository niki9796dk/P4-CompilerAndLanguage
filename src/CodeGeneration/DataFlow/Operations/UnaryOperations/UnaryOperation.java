package CodeGeneration.DataFlow.Operations.UnaryOperations;

import CodeGeneration.DataFlow.Network.Interfaces.Channel;
import CodeGeneration.DataFlow.Network.ListChannel;
import CodeGeneration.DataFlow.Operations.Operation;
import LinearAlgebra.Types.Matrices.Matrix;
import LinearAlgebra.Types.Matrices.MatrixBuilder;
import MachineLearning.NeuralNetwork.ANN.ActivactionFunctions.ActivationFunction;

public abstract class UnaryOperation extends Operation {
    protected UnaryOperation() {
        // Define inputs
        Channel in = new ListChannel();
        in.addTarget(this);

        // Define outputs
        Channel out = new ListChannel(this);

        // Store channels
        this
                .addInput("in", in)
                .addOutput("out", out);
    }

    @Override
    public void performOperation() {
        Matrix in1 = this.getInputValue("in1");

        this.result = operation(in1);
        print.say("performOperation() -> result = " + this.result);
    }

    @Override
    public Channel getOutputChannel() {
        return this.getChannel("out");
    }

    protected abstract Matrix operation(Matrix in);

    protected final Matrix activationFunctionUnitwise(Matrix in, ActivationFunction function) {
        MatrixBuilder b = new MatrixBuilder(in.getRows(),in.getColumns(),true);

        b.compLoop((r,c,v) -> b.setEntry(r,c, function.function(in.getEntry(r,c))));

        return b.buildDenseMatrix();
    }
}
