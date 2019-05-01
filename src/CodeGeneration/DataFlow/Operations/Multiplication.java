package CodeGeneration.DataFlow.Operations;

import LinearAlgebra.Types.Matrices.Matrix;

public class Multiplication extends BinaryOperation {
    @Override
    Matrix operation(Matrix in1, Matrix in2) {
        return in1.mult(in2);
    }
}
