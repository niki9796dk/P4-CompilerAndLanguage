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

public class mainTemplate {
    private static Print print = new Print(AnsiColor.YELLOW, "Main");

    @SuppressWarnings("Duplicates")
    public static void main(String[] args) {
        print.say("main()");

        ////////// Block Decelerations //////////

        /// Sources ///

        /// Blocks ///

        /// Operations ///


        ////////// Sources //////////


        ////////// Prints //////////



        ///////// EOF //////////
        print.say("End of main.");
    }
}
