package CodeGeneration.DataFlow.Operations.UnaryOperations.MatrixOperations;

import CodeGeneration.DataFlow.Operations.BinaryOperations.BinaryOperation;
import CodeGeneration.DataFlow.Operations.UnaryOperations.UnaryOperation;
import LinearAlgebra.Types.Matrices.Matrix;

public class Transpose extends UnaryOperation {
    @Override
    protected Matrix operation(Matrix in) {
        return in.transposeSoft();
    }
}
