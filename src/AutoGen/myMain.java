package AutoGen;

import AutoGen.CodeGen.input.Network;
import CodeGeneration.DataFlow.Network.Nodes.Block;
import Enums.AnsiColor;
import LinearAlgebra.Statics.Matrices;
import LinearAlgebra.Types.Matrices.Matrix;
import LinearAlgebra.Types.Matrices.MatrixBuilder;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class myMain {
    public static void main(String[] args) {
        // Create input data
        Matrix input = new MatrixBuilder(new double[][]{
                {0,0},
                {0,1},
                {1,0},
                {1,1}}, true).build();

        // Create target data
        Matrix target = new MatrixBuilder(new double[][]{
                {0},
                {1},
                {1},
                {0}}, true).build();

        // Create block instance
        Block network = new Network();

        // Test network before training
        Matrix prePrediction = network.evaluateInput(input);

        // Train the network
        network.train(input, target, 10000, 0.5);

        // Test network after training
        Matrix postPrediction = network.evaluateInput(input);

        // Print comparison
        System.out.println("Pre-Training:");
        System.out.println(AnsiColor.RED.toString() + Matrices.simplify(prePrediction) + AnsiColor.RESET + "\n");

        System.out.println("Post-Training:");
        System.out.println(AnsiColor.GREEN.toString() + Matrices.simplify(postPrediction) + AnsiColor.RESET);
    }
}
