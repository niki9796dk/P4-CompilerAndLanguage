package CodeGeneration.DataFlow.Operations.UnaryOperations.UnitWiseOperations;

import CodeGeneration.DataFlow.Operations.UnaryOperations.UnaryAbstractOperation;
import LinearAlgebra.Types.Matrices.Matrix;
import MachineLearning.NeuralNetwork.ANN.ActivactionFunctions.ActivationFunction;
import MachineLearning.NeuralNetwork.ANN.ActivactionFunctions.TanhActivation;

public class _Tanh extends UnaryAbstractOperation {
    private static ActivationFunction function = new TanhActivation();

    @Override
    protected Matrix operation(Matrix in) {
        return this.activationFunctionUnitwise(in, function);
    }
}
