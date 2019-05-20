package CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.UnaryOperations.UnitWiseOperations;

import CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.UnaryOperations.UnaryFunctionAbstractOperation;
import LinearAlgebra.Types.Matrices.Matrix;
import MachineLearning.NeuralNetwork.ANN.ActivactionFunctions.ActivationFunction;
import MachineLearning.NeuralNetwork.ANN.ActivactionFunctions.TanhActivation;

public class _Tanh extends UnaryFunctionAbstractOperation {
    private static ActivationFunction function = new TanhActivation();

    @Override
    protected ActivationFunction getFunction() {
        return function;
    }
}
