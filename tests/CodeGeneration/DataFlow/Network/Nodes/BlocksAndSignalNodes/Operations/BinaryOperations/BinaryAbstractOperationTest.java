package CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.BinaryOperations;

import CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.BinaryOperations.MatrixOperations.Multiplication;
import CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.BinaryOperations.UnitWiseOperations._Addition;
import CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.BinaryOperations.UnitWiseOperations._Division;
import CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.BinaryOperations.UnitWiseOperations._Multiplication;
import CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.BinaryOperations.UnitWiseOperations._Subtraction;
import CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.NullaryOperation.Source;
import CodeGeneration.DataFlow.Network.Nodes.SignalNodes.Channels.ListChannel;
import CodeGeneration.Utility.Print;
import Enums.AnsiColor;
import LinearAlgebra.Types.Matrices.Matrix;
import LinearAlgebra.Types.Matrices.MatrixBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BinaryAbstractOperationTest {

    private ArrayList<BinaryAbstractOperation> operations;
    private ArrayList<Matrix> expectedResultsOperations;
    private Source in1, in2;

    @BeforeEach
    void beforeEach() {
        operations = new ArrayList<>();

        operations.add(new Multiplication());
        operations.add(new _Addition());
        operations.add(new _Division());
        operations.add(new _Multiplication());
        operations.add(new _Subtraction());

        in1 = new Source(new MatrixBuilder()
                .addRow(1, 2)
                .addRow(3, 4)
        );
        in2 = new Source(new MatrixBuilder()
                .addRow(3, 1)
                .addRow(2, 4)
        );

        expectedResultsOperations = new ArrayList<>();

        expectedResultsOperations.add(new MatrixBuilder()
                .addRow(7, 9)
                .addRow(17, 19)
                .buildDenseMatrix()
        );
        expectedResultsOperations.add(new MatrixBuilder()
                .addRow(4, 3)
                .addRow(5, 8)
                .buildDenseMatrix()
        );
        expectedResultsOperations.add(new MatrixBuilder()
                .addRow(1d / 3d, 2)
                .addRow(1.5, 1)
                .buildDenseMatrix()
        );

        expectedResultsOperations.add(new MatrixBuilder()
                .addRow(3, 2)
                .addRow(6, 16)
                .buildDenseMatrix()
        );

        expectedResultsOperations.add(new MatrixBuilder()
                .addRow(-2, 1)
                .addRow(1, 0)
                .buildDenseMatrix()
        );


    }

    @RepeatedTest(5)
    void performOperation(RepetitionInfo r) {
        int i = r.getCurrentRepetition() - 1;

        BinaryAbstractOperation op = operations.get(i);
        Matrix expected = expectedResultsOperations.get(i);

        in1.connectTo(op, "out", BinaryAbstractOperation.BINARY_IN_A_CHANNEL);
        in2.connectTo(op, "out", BinaryAbstractOperation.BINARY_IN_B_CHANNEL);
        op.performOperation();
        Matrix result = op.getOutputChannel().getResult();

        Print.echo(AnsiColor.PURPLE, op.getOutputChannel().getResult().toString());
        assertEquals(result, expected);

    }

    @RepeatedTest(5)
    void getOutputChannel(RepetitionInfo r) {
        int i = r.getCurrentRepetition()-1;
        BinaryAbstractOperation op = operations.get(i);

        assertNotNull(op.getOutputChannel());
    }

    @Test
    void addInput(){
        assertThrows(IllegalArgumentException.class, () -> operations.get(0).addNewInputLabel("in3", new ListChannel()));
        operations.get(0).addNewInputLabel(BinaryAbstractOperation.BINARY_IN_A_CHANNEL, new ListChannel());
        operations.get(0).addNewInputLabel(BinaryAbstractOperation.BINARY_IN_A_CHANNEL, new ListChannel());
    }
}
