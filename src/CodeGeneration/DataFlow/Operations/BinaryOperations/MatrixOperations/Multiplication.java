package CodeGeneration.DataFlow.Operations.BinaryOperations.MatrixOperations;

import CodeGeneration.DataFlow.Operations.BinaryOperations.BinaryOperation;
import LinearAlgebra.Types.Matrices.Matrix;

public class Multiplication extends BinaryOperation {
    @Override
    protected Matrix operation(Matrix in1, Matrix in2) {
        return in1.mult(in2);
    }
}
