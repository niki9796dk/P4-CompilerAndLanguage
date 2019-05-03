package CodeGeneration.DataFlow.Operations.Nullary;

import CodeGeneration.DataFlow.Network.AbstractBlock;
import CodeGeneration.DataFlow.Network.Interfaces.Channel;
import CodeGeneration.DataFlow.Network.Interfaces.SignalNode;
import CodeGeneration.DataFlow.Network.ListChannel;
import CodeGeneration.DataFlow.Operations.AbstractOperation;
import CodeGeneration.DataFlow.Operations.UnaryOperations.IllegalMethodException;
import Enums.AnsiColor;
import LinearAlgebra.Types.Matrices.Matrix;

public abstract class NullaryAbstractOperation extends AbstractOperation {
    /**
     * Operation constructor that makes 2 input-channels which is the operands, and an output-channel which is the result
     */
    protected NullaryAbstractOperation() {
        // Define outputs
        Channel out = new ListChannel(this);

        // Store channels
        this.addNewOutputLabel("out", out);
    }

    /**
     * Run the specified operation on in1 and in2 and update result field
     */
    @Override
    public void performOperation() {
        this.result = operation();
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

    /**
     * @return the content matrix.
     */
    protected abstract Matrix operation();

    @Override
    public AbstractBlock addNewInputLabel(String id, Channel c) {
        throw new IllegalMethodException("Can not add input to a Unary Operation");
    }

    /*
    @Override
    public void traverseBackwards() {
        print.say(AnsiColor.WHITE,"Nullary traverseBackwards!");

    }
    */
}
