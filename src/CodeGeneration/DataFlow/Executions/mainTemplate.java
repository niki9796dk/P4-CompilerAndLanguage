package CodeGeneration.DataFlow.Executions;

import CodeGeneration.utility.Print;
import Enums.AnsiColor;

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
