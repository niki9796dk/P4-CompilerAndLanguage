package CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.NullaryOperation;

import CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.UnaryOperations.IllegalMethodException;
import CodeGeneration.DataFlow.Network.Nodes.SignalNodes.Channels.ListChannel;
import LinearAlgebra.Types.Matrices.Matrix;
import LinearAlgebra.Types.Matrices.MatrixBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class NullaryAbstractOperationTest {

    ArrayList<NullaryAbstractOperation> operations;
    Matrix input;

    @BeforeEach
    void beforeEach() {
        operations = new ArrayList<>();

        input = new MatrixBuilder()
                .addRow(1, 2)
                .addRow(3, 4)
                .buildDenseMatrix();

        operations.add(new Source(input));

    }

    @RepeatedTest(1)
    void performOperation(RepetitionInfo r) {
        int i = r.getCurrentRepetition() - 1;

        NullaryAbstractOperation op = operations.get(i);

        op.performOperation();

        assertEquals(op.getResult(), input);
    }

    @Test
    void addNewInputLabel() {
        assertThrows(IllegalMethodException.class, () -> operations.get(0).addNewInputLabel("in", new ListChannel()));
    }


}