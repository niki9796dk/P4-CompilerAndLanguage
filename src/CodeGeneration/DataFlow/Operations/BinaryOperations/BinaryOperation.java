package CodeGeneration.DataFlow.Operations.BinaryOperations;

import CodeGeneration.DataFlow.Network.ListChannel;
import CodeGeneration.DataFlow.Network.Interfaces.Channel;
import CodeGeneration.DataFlow.Operations.Operation;
import LinearAlgebra.Types.Matrices.Matrix;

abstract class BinaryOperation extends Operation {
    BinaryOperation() {
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

    @Override
    public Channel getOutputChannel() {
        return this.getChannel("out");
    }
}

// Binary matrix operation ("Addition", "Multiplication", "Subtraction")
// Binary unitwise operation ("_Addition", "_Multiplication", "_Subtraction", "_Division")
// unary matrix operation ("Transpose")
// Unary unitwise operation ("_Sigmoid", "_Tanh", "_Relu")
