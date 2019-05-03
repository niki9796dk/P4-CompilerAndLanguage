package CodeGeneration.DataFlow.Operations.BinaryOperations.MatrixOperations;

import CodeGeneration.DataFlow.Operations.BinaryOperations.BinaryOperation;
import LinearAlgebra.Types.Matrices.Matrix;

public class Multiplication extends BinaryOperation {
    /**
     * Matrix-wise multiplication of matrices
     *
     * @param in1 operand 1
     * @param in2 operand 2
     * @return matrix-wise multiplication of matrix in1 and in2
     */
    @Override
    protected Matrix operation(Matrix in1, Matrix in2) {
        return in1.mult(in2);
    }
}
