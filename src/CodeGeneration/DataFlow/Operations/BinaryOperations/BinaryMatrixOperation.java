package CodeGeneration.DataFlow.Operations.BinaryOperations;

import CodeGeneration.DataFlow.Operations.Operation;
import LinearAlgebra.Types.Matrices.Matrix;

public class BinaryMatrixOperation extends BinaryOperation {
    @Override
    public void performOperation() {
        float in1 = this.getInputValue("in1");
        float in2 = this.getInputValue("in2");

        this.result = operation(in1, in2);
        print.say("performOperation() -> result = " + this.result);
    }

}