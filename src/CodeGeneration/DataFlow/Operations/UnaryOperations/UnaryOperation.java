package CodeGeneration.DataFlow.Operations.UnaryOperations;

import CodeGeneration.DataFlow.Network.AbstractBlock;
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
                .addNewInputLabel("in", in)
                .addNewOutputLabel("out", out);
    }

    @Override
    public void performOperation() {
        Matrix in = this.getInputValue("in");

        if(in == null)
            throw new NullPointerException("in is null!");

        this.result = operation(in);
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

    @Override
    public AbstractBlock addNewInputLabel(String id, Channel c) {
        if(!id.equals("in"))
            throw new IllegalArgumentException("Input channel has to be in for UnaryOperation objects!");
        return super.addNewInputLabel(id, c);
    }
}
