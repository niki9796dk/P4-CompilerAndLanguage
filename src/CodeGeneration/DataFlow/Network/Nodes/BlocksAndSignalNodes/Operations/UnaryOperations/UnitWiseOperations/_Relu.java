package CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.UnaryOperations.UnitWiseOperations;

import CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.UnaryOperations.UnaryFunctionAbstractOperation;
import MachineLearning.NeuralNetwork.ANN.ActivactionFunctions.ActivationFunction;
import MachineLearning.NeuralNetwork.ANN.ActivactionFunctions.ReLUActivation;

public class _Relu extends UnaryFunctionAbstractOperation {
    private static final ActivationFunction function = new ReLUActivation();

    @Override
    protected ActivationFunction getFunction() {
        return function;
    }
}
