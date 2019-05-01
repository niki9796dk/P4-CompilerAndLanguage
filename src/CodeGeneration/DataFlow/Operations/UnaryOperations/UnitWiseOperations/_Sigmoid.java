package CodeGeneration.DataFlow.Operations.UnaryOperations.UnitWiseOperations;

import CodeGeneration.DataFlow.Operations.UnaryOperations.UnaryOperation;
import LinearAlgebra.Types.Matrices.Matrix;
import LinearAlgebra.Types.Matrices.MatrixBuilder;
import MachineLearning.NeuralNetwork.ANN.ActivactionFunctions.ActivationFunction;
import MachineLearning.NeuralNetwork.ANN.ActivactionFunctions.SigmoidActivation;

public class _Sigmoid extends UnaryOperation {
    private static ActivationFunction function = new SigmoidActivation();

    @Override
    protected Matrix operation(Matrix in) {
        return this.activationFunctionUnitwise(in, function);
    }
}
