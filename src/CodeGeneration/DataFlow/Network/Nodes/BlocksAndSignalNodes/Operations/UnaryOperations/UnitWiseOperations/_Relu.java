package CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.UnaryOperations.UnitWiseOperations;

import CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.UnaryOperations.UnaryAbstractOperation;
import LinearAlgebra.Types.Matrices.Matrix;
import MachineLearning.NeuralNetwork.ANN.ActivactionFunctions.ActivationFunction;
import MachineLearning.NeuralNetwork.ANN.ActivactionFunctions.ReLUActivation;
import TypeChecker.Exceptions.ShouldNotHappenException;

public class _Relu extends UnaryAbstractOperation {
    private static ActivationFunction function = new ReLUActivation();

    @Override
    protected ActivationFunction getFunction() {
        return function;
    }
}
