package CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.UnaryOperations.UnitWiseOperations;

import CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.UnaryOperations.UnaryFunctionAbstractOperation;
import LinearAlgebra.Types.Matrices.Matrix;
import MachineLearning.NeuralNetwork.ANN.ActivactionFunctions.ActivationFunction;
import MachineLearning.NeuralNetwork.ANN.ActivactionFunctions.SigmoidActivation;

public class _Sigmoid extends UnaryFunctionAbstractOperation {
    private static ActivationFunction function = new SigmoidActivation();

    @Override
    protected ActivationFunction getFunction() {
        return function;
    }
}
