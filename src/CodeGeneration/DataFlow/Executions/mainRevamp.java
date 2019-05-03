package CodeGeneration.DataFlow.Executions;

import CodeGeneration.DataFlow.Blocks.A_Half;
import CodeGeneration.DataFlow.Blocks.A_plus_B_mult_B;
import CodeGeneration.DataFlow.Network.Interfaces.Block;
import CodeGeneration.DataFlow.Operations.BinaryOperations.UnitWiseOperations._Addition;
import CodeGeneration.DataFlow.Operations.Nullary.Source;
import CodeGeneration.DataFlow.Operations.Operation;
import CodeGeneration.utility.Print;
import Enums.AnsiColor;
import LinearAlgebra.Types.Matrices.MatrixBuilder;

public class mainRevamp {
    private static Print print = new Print(AnsiColor.YELLOW, "Main");

    @SuppressWarnings("Duplicates")
    public static void main(String[] args) {
        print.say("main()");

        ////////// Block Decelerations //////////

        /// Sources ///
        Source source000 = new Source(new MatrixBuilder()
                .addRow(1, 2)
                .addRow(3, 4));
        Source source001 = new Source(MatrixBuilder.buildConstant(2, 2, 2));

        /// Blocks ///
        Block block000 = new A_plus_B_mult_B();
        Block block001 = new A_Half();

        /// Operations ///
        Operation operation000 = new _Addition();

        ////////// Connect the network //////////
        block000.connectTo(operation000, "out", "in1");
        block000.connectTo(operation000, "out", "in2");

        operation000.connectTo(block001, "out", "A");

        ////////// Sources, must be connected as the last part of the network. //////////
        source000.connectTo(block000, "out", "A");
        source001.connectTo(block000, "out", "B");

        ////////// Prints //////////
        print.say("block000: " + block000.getChannel("out").getResult());
        print.say("operation000: " + operation000.getChannel("out").getResult());
        print.say("block001: " + block001.getChannel("out").getResult());


        ///////// EOF //////////
        print.say("End of main.");
    }
}
