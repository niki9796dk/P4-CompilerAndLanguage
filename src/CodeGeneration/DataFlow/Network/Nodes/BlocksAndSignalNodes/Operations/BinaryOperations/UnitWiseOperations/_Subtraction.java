package CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.BinaryOperations.UnitWiseOperations;

import CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.BinaryOperations.BinaryAbstractOperation;
import LinearAlgebra.Types.Matrices.ConstantMatrix;
import LinearAlgebra.Types.Matrices.Matrix;

public class _Subtraction extends BinaryAbstractOperation {
    /**
     * Unit-wise subtraction of matrices
     *
     * @param in1 operand 1
     * @param in2 operand 2
     * @return unit-wise subtraction of matrix in1 and in2
     */
    @Override
    protected Matrix operation(Matrix in1, Matrix in2) {
        return in1.sub(in2);
    }

    @Override
    protected Matrix calculateIn1Derivatives(Matrix in2, Matrix out) {
        return out;
    }

    @Override
    protected Matrix calculateIn2Derivatives(Matrix in1, Matrix out) {
        return (new ConstantMatrix(out.getRows(), out.getColumns(), 0).sub(out));
    }
}
