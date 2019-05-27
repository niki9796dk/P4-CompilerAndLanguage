package CodeGeneration.DataFlow.Executions;

import AutoGen.CodeGen.ANN;
import CodeGeneration.DataFlow.Network.Nodes.Block;
import LinearAlgebra.Types.Matrices.Matrix;
import LinearAlgebra.Types.Matrices.MatrixBuilder;

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
