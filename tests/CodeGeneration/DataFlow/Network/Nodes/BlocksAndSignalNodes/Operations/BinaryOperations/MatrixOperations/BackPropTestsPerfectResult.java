package CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.BinaryOperations.MatrixOperations;

import CodeGeneration.DataFlow.Network.Nodes.Blocks.AbstractBlock;
import CodeGeneration.DataFlow.Network.Nodes.Blocks.BlockConfiguration;
import CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.BinaryOperations.BinaryAbstractOperation;
import CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.BinaryOperations.UnitWiseOperations._Addition;
import CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.BinaryOperations.UnitWiseOperations._Division;
import CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.BinaryOperations.UnitWiseOperations._Multiplication;
import CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.BinaryOperations.UnitWiseOperations._Subtraction;
import CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.NullaryOperation.Source;
import CodeGeneration.DataFlow.Network.Nodes.SignalNodes.BounceNodes.backpropagationBounce;
import LinearAlgebra.Types.Matrices.Matrix;
import LinearAlgebra.Types.Matrices.MatrixBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BackPropTestsPerfectResult {

    private final double delta = 0.0000000000000001d;

    private void assertClose(double a, double b) {
        assertTrue(a + delta > b && a - delta < b);
    }

    private Source s1, s2;
    private Matrix expectedCost;
    private ArrayList<BinaryAbstractOperation> operations;
    private ArrayList<Matrix> targets;

    @BeforeEach
    void beforeEach() {
        s1 = new Source(new MatrixBuilder()
                .addRow(1, 2)
                .addRow(3, 4)
        );
        s2 = new Source(new MatrixBuilder()
                .addRow(3, 1)
                .addRow(2, 4)
        );

        expectedCost = MatrixBuilder.buildConstant(2, 2, 0);

        operations = new ArrayList<>();

        operations.add(new Multiplication());
        operations.add(new _Addition());
        operations.add(new _Division());
        operations.add(new _Multiplication());
        operations.add(new _Subtraction());

        targets = new ArrayList<>();

        targets.add(new MatrixBuilder()
                .addRow(7, 9)
                .addRow(17, 19)
                .buildDenseMatrix()
        );
        targets.add(new MatrixBuilder()
                .addRow(4, 3)
                .addRow(5, 8)
                .buildDenseMatrix()
        );
        targets.add(new MatrixBuilder()
                .addRow(1d / 3d, 2)
                .addRow(1.5, 1)
                .buildDenseMatrix()
        );

        targets.add(new MatrixBuilder()
                .addRow(3, 2)
                .addRow(6, 16)
                .buildDenseMatrix()
        );

        targets.add(new MatrixBuilder()
                .addRow(-2, 1)
                .addRow(1, 0)
                .buildDenseMatrix()
        );

    }

    @RepeatedTest(5)
    void flawedResult(RepetitionInfo r) {
        BinaryAbstractOperation operation = operations.get(r.getCurrentRepetition() - 1);
        Matrix target = targets.get(r.getCurrentRepetition() - 1);

        s1.connectTo(operation, Source.NULLARY_OUT_CHANNEL, BinaryAbstractOperation.BINARY_IN_A_CHANNEL);
        s2.connectTo(operation, Source.NULLARY_OUT_CHANNEL, BinaryAbstractOperation.BINARY_IN_B_CHANNEL);


        AbstractBlock.configuration = new BlockConfiguration(2, 0.1);

        new backpropagationBounce(target)
                .connectToMainBlock(operation);

        s1.acceptReadySignal();
        s2.acceptReadySignal();

        operation.operationBackpropagation(s1.getOutputChannel(), s2.getOutputChannel(), operation.getOutputChannel());

        assertEquals(expectedCost, operation.getResultBackpropagation(operation.getInputChannels().get(BinaryAbstractOperation.BINARY_IN_A_CHANNEL)));
        assertEquals(expectedCost, operation.getResultBackpropagation(operation.getInputChannels().get(BinaryAbstractOperation.BINARY_IN_B_CHANNEL)));
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