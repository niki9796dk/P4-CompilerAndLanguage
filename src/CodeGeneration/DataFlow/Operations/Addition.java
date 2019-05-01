package CodeGeneration.DataFlow.Operations;

import LinearAlgebra.Types.Matrices.Matrix;

public class Addition extends BinaryOperation {
    @Override
    Matrix operation(Matrix in1, Matrix in2) {
        return in1.add(in2);
    }
}
