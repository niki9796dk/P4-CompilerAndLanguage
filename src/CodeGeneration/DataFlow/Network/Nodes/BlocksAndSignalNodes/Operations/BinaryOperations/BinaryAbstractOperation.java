package CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.BinaryOperations;

import CodeGeneration.DataFlow.Network.Nodes.Blocks.AbstractBlock;
import CodeGeneration.DataFlow.Network.Nodes.SignalNodes.Channel;
import CodeGeneration.DataFlow.Network.Nodes.SignalNodes.Channels.ListChannel;
import CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.AbstractOperation;
import DataStructures.Pair;
import Enums.AnsiColor;
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

    @Override
    public void performBackpropagationOperation() {
        Matrix in1 = this.getInputValue("in1");
        Matrix in2 = this.getInputValue("in2");
        Matrix out = this.getOutputChannel().getResult();

        if (in1 == null)
            throw new NullPointerException("in1 is null!");

        if (in2 == null)
            throw new NullPointerException("in2 is null!");

        if (out == null)
            throw new NullPointerException("out is null!");

        this.operationBackpropagation(in1,in2,out);
        print.say("performOperation() -> result = " + this.resultBackpropagation[0] + "\n" + this.resultBackpropagation[1]);
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

    protected abstract Matrix operationBackpropagation(Matrix in1, Matrix in2, Matrix out);
    //@OverrideoperationBackpropagation
    //Todo; Make this an abstract method

    /*
    protected void operationBackpropagation(Matrix in1, Matrix in2, Matrix out) {
        print.say(AnsiColor.RED,"operationBackpropagation() has been called, yet it is not implemented!");

        this.resultBackpropagation[0] = null;
        this.resultBackpropagation[1] = null;
    }
    */

    @Override
    public AbstractBlock addNewInputLabel(String id, Channel c) {
        if (!id.equals("in1") && !id.equals("in2"))
            throw new IllegalArgumentException("Input channel has to be in1 or in2 for BinaryOperation objects!");
        return super.addNewInputLabel(id, c);
    }
}
