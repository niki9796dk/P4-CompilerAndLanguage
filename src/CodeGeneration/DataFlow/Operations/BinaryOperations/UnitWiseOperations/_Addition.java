package CodeGeneration.DataFlow.Operations.BinaryOperations.UnitWiseOperations;

import CodeGeneration.DataFlow.Operations.BinaryOperations.BinaryOperation;
import LinearAlgebra.Types.Matrices.Matrix;

public class _Addition extends BinaryOperation {
    /**
     * Unit-wise addition of matrices
     * @param in1 operand 1
     * @param in2 operand 2
     * @return unit-wise addition of matrix in1 and in2
     */
    @Override
    protected Matrix operation(Matrix in1, Matrix in2) {
        return in1.add(in2);
    }
}
