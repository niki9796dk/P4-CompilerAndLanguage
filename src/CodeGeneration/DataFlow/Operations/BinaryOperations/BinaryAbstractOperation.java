package CodeGeneration.DataFlow.Operations.BinaryOperations;

import CodeGeneration.DataFlow.Network.AbstractBlock;
import CodeGeneration.DataFlow.Network.Interfaces.Channel;
import CodeGeneration.DataFlow.Network.ListChannel;
import CodeGeneration.DataFlow.Operations.AbstractOperation;
import LinearAlgebra.Types.Matrices.Matrix;

public abstract class BinaryAbstractOperation extends AbstractOperation {
    /**
     * Operation constructor that makes 2 input-channels which is the operands, and an output-channel which is the result
     */
    protected BinaryAbstractOperation() {
        // Define inputs
        Channel in1 = new ListChannel().addTarget(this);
        Channel in2 = new ListChannel().addTarget(this);

        // Define outputs
        Channel out = new ListChannel().setSource(this);

        // Store channels
        this
                .addNewInputLabel("in1", in1)
                .addNewInputLabel("in2", in2)
                .addNewOutputLabel("out", out);
    }

    /**
     * Run the specified operation on in1 and in2 and update result field
     */
    @Override
    public void performOperation() {
        Matrix in1 = this.getInputValue("in1");
        Matrix in2 = this.getInputValue("in2");

        if (in1 == null)
            throw new NullPointerException("in1 is null!");

        if (in2 == null)
            throw new NullPointerException("in2 is null!");

        this.result = operation(in1, in2);
        print.say("performOperation() -> result = " + this.result);
    }

    /**
     * Get channel with specified id 'out'
     *
     * @return output channel
     */
    @Override
    public Channel getOutputChannel() {
        return this.getChannel("out");
    }

    protected abstract Matrix operation(Matrix in1, Matrix in2);

    @Override
    public AbstractBlock addNewInputLabel(String id, Channel c) {
        if (!id.equals("in1") && !id.equals("in2"))
            throw new IllegalArgumentException("Input channel has to be in1 or in2 for BinaryOperation objects!");
        return super.addNewInputLabel(id, c);
    }
}
