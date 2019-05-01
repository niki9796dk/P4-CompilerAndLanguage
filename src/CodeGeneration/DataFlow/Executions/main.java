package CodeGeneration.DataFlow.Executions;

import CodeGeneration.DataFlow.Blocks.A_plus_B_mult_B;
import CodeGeneration.DataFlow.Network.ChannelId;
import CodeGeneration.DataFlow.Network.Interfaces.Block;
import CodeGeneration.DataFlow.Network.Interfaces.Channel;
import CodeGeneration.DataFlow.Network.Interfaces.SignalNode;

public class main {
    public static void main(String[] args) {
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

        System.out.println("Value of out: ┏━ " + block.getChannel("B").getValue());
        System.out.println("Should be     ┗━ 24.0");
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
