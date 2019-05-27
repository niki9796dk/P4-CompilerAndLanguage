package CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.UnaryOperations.UnitWiseOperations;

import CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.UnaryOperations.UnaryFunctionAbstractOperation;
import MachineLearning.NeuralNetwork.ANN.ActivactionFunctions.ActivationFunction;
import MachineLearning.NeuralNetwork.ANN.ActivactionFunctions.TanhActivation;

public class _Tanh extends UnaryFunctionAbstractOperation {
    private static final ActivationFunction function = new TanhActivation();

    @Override
    protected ActivationFunction getFunction() {
        return function;
    }
}
