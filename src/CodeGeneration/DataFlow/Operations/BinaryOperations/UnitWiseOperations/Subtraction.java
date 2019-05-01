package CodeGeneration.DataFlow.Operations.BinaryOperations.UnitWiseOperations;

import CodeGeneration.DataFlow.Operations.BinaryOperations.BinaryOperation;
import LinearAlgebra.Types.Matrices.Matrix;

public class Subtraction extends BinaryOperation {
    @Override
    protected Matrix operation(Matrix in1, Matrix in2) {
        return in1.sub(in2);
    }
}
