package CodeGeneration.DataFlow.Operations;

import CodeGeneration.DataFlow.Network.ListChannel;
import CodeGeneration.DataFlow.Network.Interfaces.Channel;

abstract class BinaryOperation extends Operation {
    BinaryOperation() {
        // Define inputs
        Channel A = new ListChannel();
        Channel B = new ListChannel();
        A.addTarget(this);
        B.addTarget(this);

        // Define outputs
        Channel C = new ListChannel(this);

        // Store channels
        this
                .addInput("A", A)
                .addInput("B", B)
                .addOutput("C", C);
    }

    @Override
    Channel getOutputChannel() {
        return this.getChannel("C");
    }

    @Override
    public void performOperation() {
        float a = this.getInputValue("A");
        float b = this.getInputValue("B");

        this.value = operation(a, b);
        print.say("performOperation() -> this.value = " + this.value);
    }

    abstract float operation(float a, float b);
}
