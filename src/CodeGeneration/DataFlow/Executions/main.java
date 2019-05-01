package CodeGeneration.DataFlow.Executions;

import CodeGeneration.DataFlow.Blocks.A_plus_B_mult_B;
import CodeGeneration.DataFlow.Network.Interfaces.Block;
import CodeGeneration.DataFlow.Network.Interfaces.Channel;
import CodeGeneration.DataFlow.Network.Interfaces.SignalNode;
import CodeGeneration.utility.Print;
import Enums.AnsiColor;

public class main {
    private static Print print = new Print(AnsiColor.YELLOW,"Main");
    public static void main(String[] args) {
        print.say("Running");
        Block block = new A_plus_B_mult_B();

        Channel A = block.getChannel("A");
        Channel B = block.getChannel("B");

        // Set input A connect 10
        Source input1 = new Source(10);
        A.setSource(input1);
        A.acceptReadySignal();


        // Set input B connect 2
        Source input2 = new Source(2);
        B.setSource(input2);
        B.acceptReadySignal();

        // Get output

        print.say("Value of out: " + block.getChannel("C").getValue() +
                "\nShould be    24.0" +
                "\nEnd of Main");
    }

    public static class Source implements SignalNode {
        private float value;

        public Source(float value) {
            this.value = value;
        }

        @Override
        public void acceptReadySignal() {
            // Do nothing
        }

        @Override
        public float getValue() {
            return this.value;
        }
    }
}
