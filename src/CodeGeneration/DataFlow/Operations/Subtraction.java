package CodeGeneration.DataFlow.Operations;

import LinearAlgebra.Types.Matrices.Matrix;

public class Subtraction extends BinaryOperation {

    @Override
    Matrix operation(Matrix a, Matrix b) {
        return a.sub(b);
    }
}
