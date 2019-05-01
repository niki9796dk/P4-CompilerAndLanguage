package CodeGeneration.DataFlow.Operations.BinaryOperations.MatrixOperations;

import CodeGeneration.DataFlow.Operations.BinaryOperations.BinaryOperation;
import LinearAlgebra.Types.Matrices.Matrix;

public class Addition extends BinaryOperation {
    @Override
    protected Matrix operation(Matrix in1, Matrix in2) {
        return in1.add(in2);
    }
}
