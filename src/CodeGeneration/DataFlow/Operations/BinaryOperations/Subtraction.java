package CodeGeneration.DataFlow.Operations.BinaryOperations;

import LinearAlgebra.Types.Matrices.Matrix;

public class Subtraction extends BinaryMatrixOperation {
    @Override
    Matrix operation(Matrix in1, Matrix in2) {
        return in1.sub(in2);
    }
}
