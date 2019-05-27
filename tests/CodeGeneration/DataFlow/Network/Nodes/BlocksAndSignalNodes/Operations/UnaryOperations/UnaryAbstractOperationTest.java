package CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.UnaryOperations;

import CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.NullaryOperation.Source;
import CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.UnaryOperations.MatrixOperations.Transpose;
import CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.UnaryOperations.UnitWiseOperations._Relu;
import CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.UnaryOperations.UnitWiseOperations._Sigmoid;
import CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.UnaryOperations.UnitWiseOperations._Tanh;
import CodeGeneration.DataFlow.Network.Nodes.SignalNodes.Channels.ListChannel;
import LinearAlgebra.Types.Matrices.Matrix;
import LinearAlgebra.Types.Matrices.MatrixBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class UnaryAbstractOperationTest {

    ArrayList<UnaryAbstractOperation> operations;
    ArrayList<Matrix> results;
    Matrix input;

    @BeforeEach
    void beforeEach() {
        operations = new ArrayList<>();

        input = new MatrixBuilder()
                .addRow(1, 2)
                .addRow(3, 4)
                .buildDenseMatrix();

        operations.add(new Transpose());
        operations.add(new _Relu());
        operations.add(new _Sigmoid());
        operations.add(new _Tanh());


        results = new ArrayList<>();

        results.add(new MatrixBuilder()
                .addRow(1, 3)
                .addRow(2, 4)
                .buildDenseMatrix());


        results.add(new MatrixBuilder()
                .addRow(1, 2)
                .addRow(3, 4)
                .buildDenseMatrix());

        results.add(new MatrixBuilder()
                .addRow(0.7310585786300049, 0.8807970779778823)
                .addRow(0.9525741268224334, 0.9820137900379085)
                .buildDenseMatrix());

        results.add(new MatrixBuilder()
                .addRow(0.7615941559557649, 0.9640275800758169)
                .addRow(0.9950547536867305, 0.999329299739067)
                .buildDenseMatrix());

    }

    @RepeatedTest(4)
    void getOutputChannel(RepetitionInfo r) {
        int i = r.getCurrentRepetition() - 1;
        UnaryAbstractOperation op = operations.get(i);

        assertSame(op.getOutputChannel(), op.getFirstOutput());
        assertSame(op.getOutputs().iterator().next(), op.getOutputChannel());
    }

    @RepeatedTest(4)
    void operation(RepetitionInfo r) {
        int i = r.getCurrentRepetition() - 1;
        UnaryAbstractOperation op = operations.get(i);

        new Source(input).connectTo(op, Source.NULLARY_OUT_CHANNEL, UnaryAbstractOperation.UNARY_IN_CHANNEL);
        op.performOperation();

        assertEquals(op.getResult(),results.get(i));
    }

    @RepeatedTest(4)
    void addNewInputLabel(RepetitionInfo r) {
        int i = r.getCurrentRepetition() - 1;
        UnaryAbstractOperation op = operations.get(i);

        assertThrows(IllegalArgumentException.class, ()-> op.addNewInputLabel("in1",new ListChannel()));
        op.addNewInputLabel(UnaryAbstractOperation.UNARY_IN_CHANNEL, new ListChannel());

    }
}