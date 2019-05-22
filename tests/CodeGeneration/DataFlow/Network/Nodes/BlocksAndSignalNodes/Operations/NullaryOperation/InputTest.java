package CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.NullaryOperation;

import CodeGeneration.DataFlow.Executions.BlockWrapper;
import CodeGeneration.DataFlow.Network.Nodes.Block;
import CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operation;
import CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.BinaryOperations.MatrixOperations.Multiplication;
import CodeGeneration.DataFlow.Network.Nodes.SignalNodes.Channels.ListChannel;
import LinearAlgebra.Statics.Matrices;
import LinearAlgebra.Types.Matrices.Matrix;
import LinearAlgebra.Types.Matrices.MatrixBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InputTest {

    // Fields:
    private Input input01;
    private Input input02;
    private Input input03;

    private Matrix matrix;
    private MatrixBuilder matrixBuilder;

    private BlockWrapper blockWrapper;
    private Operation operation;

    @BeforeEach
    void beforeEach() {
        this.matrix = Matrices.randomMatrix(2, 2);
        this.matrixBuilder = new MatrixBuilder();

        this.input01 = new Input();
        this.input02 = new Input(this.matrix);
        this.input03 = new Input(this.matrixBuilder);

        this.operation = new Multiplication();

        this.blockWrapper = new BlockWrapper();
    }

    @Test
    void setInput() {
        this.input01.setInput(this.matrix);
        assertEquals(this.matrix, this.input01.operation());
    }

    @Test
    void setInput1() {
        this.input01.setInput(this.matrixBuilder);
        assertEquals(this.matrixBuilder.build(), this.input01.operation());
    }

    @Test
    void operation() {
        this.input01.setInput(this.matrix);
        assertEquals(this.matrix, this.input01.operation());
    }

    @Test
    void connectTo01() {
        assertThrows(NullPointerException.class, () -> this.input01.connectTo(new BlockWrapper(), "", ""));
    }
}