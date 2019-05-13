package CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.UnaryOperations.MatrixOperations;

import CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.UnaryOperations.UnaryAbstractOperation;
import LinearAlgebra.Types.Matrices.Matrix;
import MachineLearning.NeuralNetwork.ANN.ActivactionFunctions.ActivationFunction;
import jdk.jshell.spi.ExecutionControl;

public class Transpose extends UnaryAbstractOperation {

    @Override
    protected Matrix operation(Matrix in) {
        return in.transposeSoft();
    }

    @Override
    protected Matrix calculateInDerivatives(Matrix in, Matrix out) {
        return out.transpose();
    }

    @Override
    protected ActivationFunction getFunction() {
        throw new RuntimeException("Not implemented");
    }
}
