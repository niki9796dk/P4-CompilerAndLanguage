package CodeGeneration.DataFlow.Operations.BinaryOperations.UnitWiseOperations;

import CodeGeneration.DataFlow.Operations.BinaryOperations.BinaryOperation;
import LinearAlgebra.Types.Matrices.Matrix;
import LinearAlgebra.Types.Matrices.MatrixBuilder;

public class _Division extends BinaryOperation {
    /**
     * Unit-wise division of matrices
     * @param in1 operand 1
     * @param in2 operand 2
     * @return unit-wise division of matrix in1 and in2
     */
    @Override
    protected Matrix operation(Matrix in1, Matrix in2) {
        MatrixBuilder out = new MatrixBuilder(in1.getRows(), in1.getColumns(), true);
        return out.divisionToEntries(in2).build();
    }
}
