package CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.BinaryOperations;

import CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.BinaryOperations.MatrixOperations.Multiplication;
import CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.BinaryOperations.UnitWiseOperations._Addition;
import CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.BinaryOperations.UnitWiseOperations._Division;
import CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.BinaryOperations.UnitWiseOperations._Multiplication;
import CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.BinaryOperations.UnitWiseOperations._Subtraction;
import CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.NullaryOperation.Input;
import CodeGeneration.utility.Print;
import Enums.AnsiColor;
import LinearAlgebra.Types.Matrices.Matrix;
import LinearAlgebra.Types.Matrices.MatrixBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BinaryAbstractOperationTest {

    ArrayList<BinaryAbstractOperation> operations;
    ArrayList<Matrix> expectedResults;
    Input in1, in2;

    @BeforeEach
    void beforeEach() {
        operations = new ArrayList<>();

        operations.add(new Multiplication());
        operations.add(new _Addition());
        operations.add(new _Division());
        operations.add(new _Multiplication());
        operations.add(new _Subtraction());

        in1 = new Input(new MatrixBuilder()
                .addRow(1, 2)
                .addRow(3, 4)
        );
        in2 = new Input(new MatrixBuilder()
                .addRow(3, 1)
                .addRow(2, 4)
        );

        expectedResults = new ArrayList<>();

        expectedResults.add(new MatrixBuilder()
                .addRow(7, 9)
                .addRow(17, 19)
                .buildDenseMatrix()
        );
        expectedResults.add(new MatrixBuilder()
                .addRow(4, 3)
                .addRow(5, 8)
                .buildDenseMatrix()
        );
        expectedResults.add(new MatrixBuilder()
                .addRow(1d / 3d, 2)
                .addRow(1.5, 1)
                .buildDenseMatrix()
        );

        expectedResults.add(new MatrixBuilder()
                .addRow(3, 2)
                .addRow(6, 16)
                .buildDenseMatrix()
        );

        expectedResults.add(new MatrixBuilder()
                .addRow(-2, 1)
                .addRow(1, 0)
                .buildDenseMatrix()
        );


    }

    @Test
    void performOperation() {

        for (int i = 0; i < operations.size(); i++) {
            BinaryAbstractOperation op = operations.get(i);
            Matrix expected = expectedResults.get(i);

            in1.connectTo(op, "out", "in1");
            in2.connectTo(op, "out", "in2");
            op.performOperation();
            Matrix result = op.getOutputChannel().getResult();

            Print.echo(AnsiColor.PURPLE, op.getOutputChannel().getResult().toString());
            assertEquals(result, expected);
        }
    }

    @Test
    void performBackpropagationOperation() {
        /*
        for (int i = 0; i < operations.size(); i++) {
            BinaryAbstractOperation op = operations.get(i);
            Matrix expected = expectedResults.get(i);

            in1.connectTo(op, "out", "in1");
            in2.connectTo(op, "out", "in2");
            op.performOperation();
            Matrix result = op.getOutputChannel().getResultBackpropagation();

            Print.echo(AnsiColor.PURPLE, op.getOutputChannel().getResult().toString());
            assertEquals(result, expected);
        }

         */
    }

    @Test
    void getOutputChannel() {
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

    @Test
    void addNewInputLabel() {
    }
}
