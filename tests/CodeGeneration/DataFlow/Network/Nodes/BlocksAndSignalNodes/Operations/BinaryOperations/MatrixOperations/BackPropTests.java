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

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BackPropTests {

    private Source s1, s2;
    private Matrix target;
    private ArrayList<BinaryAbstractOperation> operations;
    private ArrayList<Matrix> expectedCosts;

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

        target = MatrixBuilder.buildConstant(2, 2, 1);

        operations = new ArrayList<>();

        operations.add(new Multiplication());
        operations.add(new _Addition());
        operations.add(new _Division());
        operations.add(new _Multiplication());
        operations.add(new _Subtraction());

        expectedCosts = new ArrayList<>();

        expectedCosts.add(new MatrixBuilder().addRow(0.3899999999999997, 1.4299999999999993).addRow(0.08999999999999792, -1.970000000000002).build());
        expectedCosts.add(new MatrixBuilder().addRow(-0.10200000000000062, 0.0539999999999996).addRow(-1.138000000000001, -0.0740000000000012).build());
        expectedCosts.add(new MatrixBuilder().addRow(2.7, 1.7999999999999998).addRow(3.5999999999999996, 6.3).build());
        expectedCosts.add(new MatrixBuilder().addRow(2.7, 1.7999999999999998).addRow(3.5999999999999996, 6.3).build());
        expectedCosts.add(new MatrixBuilder().addRow(-0.2211217743525022, 0.702479338842975).addRow(0.2377095534319317, 0.0).build());
        expectedCosts.add(new MatrixBuilder().addRow(0.07461834907074548, -1.2453042824943645).addRow(-0.3517807013636637, -0.0).build());
        expectedCosts.add(new MatrixBuilder().addRow(2.986999999999999, 0.6794999999999999).addRow(2.65625, 0.0).build());
        expectedCosts.add(new MatrixBuilder().addRow(0.7209999999999999, 1.4722499999999998).addRow(5.3125, 0.0).build());
        expectedCosts.add(new MatrixBuilder().addRow(-2.7, 0.0).addRow(0.0, -0.9000000000000004).build());
        expectedCosts.add(new MatrixBuilder().addRow(2.7, 0.0).addRow(0.0, 0.9000000000000004).build());
    }

    @RepeatedTest(5)
    void flawedResult(RepetitionInfo r) {
        BinaryAbstractOperation operation = operations.get(r.getCurrentRepetition() - 1);

        s1.connectTo(operation, Source.NULLARY_OUT_CHANNEL, BinaryAbstractOperation.BINARY_IN_A_CHANNEL);
        s2.connectTo(operation, Source.NULLARY_OUT_CHANNEL, BinaryAbstractOperation.BINARY_IN_B_CHANNEL);


        AbstractBlock.configuration = new BlockConfiguration(2, 0.1);

        new backpropagationBounce(target)
                .connectToMainBlock(operation);

        s1.acceptReadySignal();
        s2.acceptReadySignal();

        operation.operationBackpropagation(s1.getOutputChannel(), s2.getOutputChannel(), operation.getOutputChannel());

        int expectedIndex = (r.getCurrentRepetition() - 1) * 2;

        assertEquals(expectedCosts.get(expectedIndex + 0), operation.getResultBackpropagation(operation.getInputChannels().get(BinaryAbstractOperation.BINARY_IN_A_CHANNEL)));
        assertEquals(expectedCosts.get(expectedIndex + 1), operation.getResultBackpropagation(operation.getInputChannels().get(BinaryAbstractOperation.BINARY_IN_B_CHANNEL)));
    }
}