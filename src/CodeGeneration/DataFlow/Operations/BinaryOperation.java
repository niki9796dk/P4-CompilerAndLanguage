package CodeGeneration.DataFlow.Operations;

import CodeGeneration.DataFlow.Network.ListChannel;
import CodeGeneration.DataFlow.Network.Interfaces.Channel;
import CodeGeneration.DataFlow.Operations.Operation;

abstract class BinaryOperation extends Operation {
    BinaryOperation() {
        // Define inputs
        Channel in1 = new ListChannel();
        Channel in2 = new ListChannel();
        in1.addTarget(this);
        in2.addTarget(this);

        // Define outputs
        Channel out = new ListChannel(this);

        // Store channels
        this
                .addInput("in1", in1)
                .addInput("in2", in2)
                .addOutput("out", out);
    }

    @Override
    Channel getOutputChannel() {
        return this.getChannel("out");
    }

    @Override
    public void performOperation() {
        float in1 = this.getInputValue("in1");
        float in2 = this.getInputValue("in2");

        this.result = operation(in1, in2);
        print.say("performOperation() -> result = " + this.result);
    }

    abstract float operation(float in1, float in2);
}
