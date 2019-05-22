package CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.BinaryOperations.MatrixOperations;

import CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.BinaryOperations.BinaryAbstractOperation;
import CodeGeneration.DataFlow.Network.Nodes.SignalNodes.Channel;
import DataStructures.Pair;
import LinearAlgebra.Types.Matrices.Matrix;

import java.util.HashMap;
import java.util.Map;

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

    @Override
    public HashMap<Channel, Matrix> operationBackpropagation(Channel in1, Channel in2, Channel out) {
        HashMap<Channel, Matrix> backpropResults = new HashMap<>();

        backpropResults.put(in1, this.calculateIn1Derivatives(in2.getResult(), out.getResultBackpropagation()));
        backpropResults.put(in2, this.calculateIn2Derivatives(in1.getResult(), out.getResultBackpropagation()));

        return backpropResults;
    }

    @Override
    protected Matrix calculateIn1Derivatives(Matrix in2, Matrix out) {
        return out.multTrans(in2);
    }

    @Override
    protected Matrix calculateIn2Derivatives(Matrix in1, Matrix out) {
        return in1.transMult(out);
    }
}
