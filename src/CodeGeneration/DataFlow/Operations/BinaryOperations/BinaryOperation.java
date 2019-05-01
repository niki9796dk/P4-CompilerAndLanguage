package CodeGeneration.DataFlow.Operations.BinaryOperations;

import CodeGeneration.DataFlow.Network.ListChannel;
import CodeGeneration.DataFlow.Network.Interfaces.Channel;
import CodeGeneration.DataFlow.Operations.Operation;
import LinearAlgebra.Types.Matrices.Matrix;

public abstract class BinaryOperation extends Operation {
    /**
     * Operation constructor that makes 2 input-channels which is the operands, and an output-channel which is the result
     */
    protected BinaryOperation() {
        // Define inputs
        Channel in1 = new ListChannel();
        Channel in2 = new ListChannel();
        in1.addTarget(this);
        in2.addTarget(this);

        // Define outputs
        Channel out = new ListChannel(this);

        // Store channels
        this
                .addInput("in1", in1)
                .addInput("in2", in2)
                .addOutput("out", out);
    }

    /**
     * Run the specified operation on in1 and in2 and update result field
     */
    @Override
    public void performOperation() {
        Matrix in1 = this.getInputValue("in1");
        Matrix in2 = this.getInputValue("in2");

        this.result = operation(in1, in2);
        print.say("performOperation() -> result = " + this.result);
    }

    /**
     * Get channel with specified id 'out'
     * @return output channel
     */
    @Override
    public Channel getOutputChannel() {
        return this.getChannel("out");
    }

    protected abstract Matrix operation(Matrix in1, Matrix in2);
}
