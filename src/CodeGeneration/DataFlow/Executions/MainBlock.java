package CodeGeneration.DataFlow.Executions;

import AutoGen.CodeGen.ANN;
import CodeGeneration.DataFlow.Network.Nodes.Block;
import CodeGeneration.DataFlow.Network.Nodes.SignalNode;
import CodeGeneration.DataFlow.Network.Nodes.SignalNodes.BounceNode;
import CodeGeneration.DataFlow.Network.Nodes.SignalNodes.BounceNodes.FeedforwardBounce;
import CodeGeneration.DataFlow.Network.Nodes.SignalNodes.BounceNodes.backpropagationBounce;
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
        Block block = new ANN();

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

        BounceNode feedForward = new FeedforwardBounce(input);
        BounceNode backProp = new backpropagationBounce(target);

        feedForward.connectToMainBlock(block);
        backProp.connectToMainBlock(block);

        long start, end, total;
        int iter = 10000;
        start = System.currentTimeMillis();
        for (int i = 0; i < iter; i++) {
            feedForward.acceptReadySignal();
        }
        end = System.currentTimeMillis();

        total = end - start;

        System.out.println("Flow: " + total);

        MachineLearning.NeuralNetwork.ANN.ANN network = new MachineLearning.NeuralNetwork.ANN.ANN(2, 1, 0, 0, new SigmoidActivation());

        TrainingMethod trainingMethod = new MiniBatch(0.2, 4, new MSECost());
        Trainer trainer = new Trainer(network, trainingMethod);

        start = System.currentTimeMillis();
        trainer.startTraining(new DefaultData(input), new DefaultData(target), iter);
        end = System.currentTimeMillis();

        total = end - start;

        System.out.println("Lib: " + total);

        //System.out.println("Final prediction => " + block.getFirstOutput().getResult());;
    }
}
