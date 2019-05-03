package CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.BinaryOperations.UnitWiseOperations;

import CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.BinaryOperations.BinaryAbstractOperation;
import LinearAlgebra.Types.Matrices.Matrix;

public class _Addition extends BinaryAbstractOperation {

    /**
     * Unit-wise addition of matrices
     *
     * @param in1 operand 1
     * @param in2 operand 2
     * @return unit-wise addition of matrix in1 and in2
     */
    @Override
    protected Matrix operation(Matrix in1, Matrix in2) {
        return in1.add(in2);
    }
}
