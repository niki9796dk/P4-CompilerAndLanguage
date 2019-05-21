package CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.UnaryOperations.MatrixOperations;

import CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.UnaryOperations.UnaryAbstractOperation;
import CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.UnaryOperations.UnaryFunctionAbstractOperation;
import CodeGeneration.DataFlow.Network.Nodes.SignalNodes.Channel;
import LinearAlgebra.Types.Matrices.Matrix;
import MachineLearning.NeuralNetwork.ANN.ActivactionFunctions.ActivationFunction;
import jdk.jshell.spi.ExecutionControl;

import java.util.HashMap;

public class Transpose extends UnaryFunctionAbstractOperation {

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
