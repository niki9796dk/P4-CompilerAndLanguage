package CodeGeneration.DataFlow.Executions;

import CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.NullaryOperation.Source;
import CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.AbstractOperation;
import CodeGeneration.DataFlow.Network.Nodes.BlocksAndSignalNodes.Operations.UnaryOperations.MatrixOperations.Transpose;
import CodeGeneration.utility.Print;
import Enums.AnsiColor;
import LinearAlgebra.Types.Matrices.MatrixBuilder;

public class mainSourceToUnary {
    private static Print print = new Print(AnsiColor.YELLOW, "Main");

    @SuppressWarnings("Duplicates")
    public static void main(String[] args) {
        print.say("main()");

        ////////// Block Decelerations //////////

        /// Sources ///
        Source source000 = new Source(new MatrixBuilder()
                .addRow(0, 999)
                .addRow(1, 2));

        /// Operations ///
        AbstractOperation operation000 = new Transpose();

        ////////// Sources, must be connected as the last part of the network. //////////
        source000.connectTo(operation000, "out", "in");

        ////////// Prints //////////
        print.say("operation000: " + operation000.getChannel("out").getResult());


        ///////// EOF //////////
        print.say("End of main.");
    }
}
