package CodeGeneration.DataFlow.Operations.BinaryOperations;

import LinearAlgebra.Types.Matrices.Matrix;
import LinearAlgebra.Types.Matrices.MatrixBuilder;

public class _Division extends BinaryMatrixOperation {
    @Override
    Matrix operation(Matrix in1, Matrix in2) {
        MatrixBuilder out = new MatrixBuilder(in1.getRows(), in1.getColumns(), true);
        return out.divisionToEntries(in2).build();
    }
}
