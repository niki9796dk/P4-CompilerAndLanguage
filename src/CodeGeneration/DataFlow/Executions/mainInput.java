package CodeGeneration.DataFlow.Executions;

import CodeGeneration.DataFlow.Operations.Nullary.Input;
import CodeGeneration.DataFlow.Operations.AbstractOperation;
import CodeGeneration.DataFlow.Operations.UnaryOperations.MatrixOperations.Transpose;
import CodeGeneration.utility.Print;
import Enums.AnsiColor;
import LinearAlgebra.Types.Matrices.MatrixBuilder;

public class mainInput {
    private static Print print = new Print(AnsiColor.YELLOW, "Main");

    @SuppressWarnings("Duplicates")
    public static void main(String[] args) {
        print.say("main()");

        ////////// Block Decelerations //////////

        /// Sources ///
        Input input = new Input(new MatrixBuilder()
                .addRow(0, 999)
                .addRow(1, 2));

        /// Operations ///
        AbstractOperation operation000 = new Transpose();

        ////////// Sources, must be connected as the last part of the network. //////////
        input.connectTo(operation000, "out", "in");

        ////////// Prints //////////
        print.say("operation000: " + operation000.getChannel("out").getResult());

        //Change input
        input.setInput(new MatrixBuilder()
                .addRow(3, 4)
                .addRow(1, 32));
        print.say("operation000: " + operation000.getChannel("out").getResult());

        ///////// EOF //////////
        print.say("End of main.");
    }
}
