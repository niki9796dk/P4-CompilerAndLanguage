package CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.BinaryOperations.UnitWiseOperations;

import CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.BinaryOperations.BinaryAbstractOperation;
import CodeGeneration.DataFlow.Network.Nodes.SignalNodes.Channel;
import LinearAlgebra.Types.Matrices.ConstantMatrix;
import LinearAlgebra.Types.Matrices.Matrix;

import java.util.HashMap;

public class _Division extends BinaryAbstractOperation {
    /**
     * Unit-wise division of matrices
     *
     * @param in1 operand 1
     * @param in2 operand 2
     * @return unit-wise division of matrix in1 and in2
     */
    @Override
    protected Matrix operation(Matrix in1, Matrix in2) {
        return in1.compDivision(in2);
    }

    @Override
    protected Matrix calculateIn1Derivatives(Matrix in2, Matrix out) {
        return out.compDivision(in2);
    }

    @Override
    protected Matrix calculateIn2Derivatives(Matrix in1, Matrix out) {
        Matrix in2BackPropResult = this.getChannel("in2").getResult();

        if (in2BackPropResult == null) {
            throw new NullPointerException("in2BackPropResult is null");
        }

        return this.calculateIn2Derivatives(in1, in2BackPropResult, out);
    }

    private Matrix calculateIn2Derivatives(Matrix in1, Matrix in2, Matrix out) {
        return out.compMult(this.flipSign(in1.compDivision(in2.compMult(in2))));
    }

    private Matrix flipSign(Matrix matrix) {
        return (new ConstantMatrix(matrix.getRows(), matrix.getColumns(), 0)).sub(matrix);
    }
}
