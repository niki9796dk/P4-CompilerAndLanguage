package CodeGeneration.DataFlow.Executions;

import AutoGen.CodeGen.ANN;
import CodeGeneration.DataFlow.Network.Nodes.Block;
import CodeGeneration.DataFlow.Network.Nodes.SignalNode;
import CodeGeneration.DataFlow.Network.Nodes.SignalNodes.BounceNode;
import CodeGeneration.DataFlow.Network.Nodes.SignalNodes.BounceNodes.FeedforwardBounce;
import CodeGeneration.DataFlow.Network.Nodes.SignalNodes.BounceNodes.backpropagationBounce;
import LinearAlgebra.Statics.Matrices;
import LinearAlgebra.Types.Matrices.Matrix;
import LinearAlgebra.Types.Matrices.MatrixBuilder;
import MachineLearning.NeuralNetwork.ANN.ActivactionFunctions.SigmoidActivation;
import MachineLearning.NeuralNetwork.Trainer.Costs.MSECost;
import MachineLearning.NeuralNetwork.Trainer.DataStructure.DefaultData;
import MachineLearning.NeuralNetwork.Trainer.Trainer;
import MachineLearning.NeuralNetwork.Trainer.TrainingMethods.MiniBatch;
import MachineLearning.NeuralNetwork.Trainer.TrainingMethods.TrainingMethod;

public class MainBlock {
    public static void main(String[] args) {
        // Imported from src/AutoGen/CodeGen
        Block block = new ANN();

        // XOR logic gate data
        Matrix input = new MatrixBuilder(4, 2, true)
                .setRow(0, 0, 0)
                .setRow(1, 0, 1)
                .setRow(2, 1, 0)
                .setRow(3, 1, 1)
                .build();

        Matrix target = new MatrixBuilder(4, 1, true)
                .setRow(0, 0)
                .setRow(1, 1)
                .setRow(2, 1)
                .setRow(3, 0)
                .build();

        int iterations = 10000;

        block.train(input, target, iterations, 0.2);

        System.out.println(block.evaluateInput(input));
    }
}
