package CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.BinaryOperations.MatrixOperations;

import CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operation;
import CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.NullaryOperation.Input;
import CodeGeneration.utility.Print;
import Enums.AnsiColor;
import LinearAlgebra.Types.Matrices.MatrixBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MultiplicationTest {

    @Test
    void someThing() {

        Operation mult = new Multiplication();

        Input in1 = new Input(MatrixBuilder.buildConstant(2,3,2));
        Input in2 = new Input(MatrixBuilder.buildConstant(3,2,3));

        in1.connectTo(mult,"out","in1");
        in2.connectTo(mult,"out","in2");

        Print.echo(AnsiColor.PURPLE, mult.getResult() );


    }

    @Test
    void operation() {
    }

    @Test
    void operationBackpropagation() {
    }

    @Test
    void calculateIn1Derivatives() {
    }

    @Test
    void calculateIn2Derivatives() {
    }
}