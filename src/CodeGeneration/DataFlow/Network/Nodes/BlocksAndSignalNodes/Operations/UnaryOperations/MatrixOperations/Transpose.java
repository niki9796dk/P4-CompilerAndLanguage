package CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.UnaryOperations.MatrixOperations;

import CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.UnaryOperations.UnaryAbstractOperation;
import LinearAlgebra.Types.Matrices.Matrix;

public class Transpose extends UnaryAbstractOperation {

    @Override
    protected Matrix operation(Matrix in) {
        return in.transposeSoft();
    }
}
