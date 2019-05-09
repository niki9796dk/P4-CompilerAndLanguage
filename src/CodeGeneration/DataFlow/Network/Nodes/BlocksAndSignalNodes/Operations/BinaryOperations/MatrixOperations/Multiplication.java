package CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.BinaryOperations.MatrixOperations;

import CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.BinaryOperations.BinaryAbstractOperation;
import DataStructures.Pair;
import LinearAlgebra.Types.Matrices.Matrix;

public class Multiplication extends BinaryAbstractOperation {
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
