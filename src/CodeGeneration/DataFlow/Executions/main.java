package CodeGeneration.DataFlow.Executions;

import CodeGeneration.DataFlow.Blocks.A_plus_B_mult_B;
import CodeGeneration.DataFlow.Network.Interfaces.Block;
import CodeGeneration.DataFlow.Network.Interfaces.Channel;
import CodeGeneration.DataFlow.Network.Interfaces.SignalNode;
import CodeGeneration.utility.Print;
import Enums.AnsiColor;
import LinearAlgebra.Types.Matrices.Matrix;
import LinearAlgebra.Types.Matrices.MatrixBuilder;

public class main {
    private static Print print = new Print(AnsiColor.YELLOW, "Main");

    public static void main(String[] args) {
        print.say("Running");
        Block block = new A_plus_B_mult_B();

        Channel A = block.getChannel("A");
        Channel B = block.getChannel("B");

        // Set input A connect 10
        Source input1 = new Source(new MatrixBuilder()
                .addRow(1, 2)
                .addRow(2, 4)
                .buildDenseMatrix()
        );
        A.setSource(input1);
        A.acceptReadySignal();


        // Set input B connect 2
        Source input2 = new Source(new MatrixBuilder()
                .addRow(2, 2)
                .addRow(3, 2)
                .buildDenseMatrix()
        );
        B.setSource(input2);
        B.acceptReadySignal();

        // Get output

        print.say("Value of out: " + block.getChannel("C").getResult() +
                "\nShould be    24.0" +
                "\nEnd of Main");
    }

    public static class Source implements SignalNode {
        private Matrix value;

        public Source(Matrix value) {
            this.value = value;
        }

        @Override
        public void acceptReadySignal() {
            // Do nothing
        }

        @Override
        public Matrix getResult() {
            return this.value;
        }
    }
}
