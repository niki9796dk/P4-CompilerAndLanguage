package CodeGeneration.DataFlow.Blocks;

import CodeGeneration.DataFlow.Network.Block;
import CodeGeneration.DataFlow.Network.Gate;
import CodeGeneration.DataFlow.Network.GateId;
import CodeGeneration.DataFlow.Network.Interfaces.Gates;
import CodeGeneration.DataFlow.Operations.Addition;
import CodeGeneration.DataFlow.Operations.Multiplication;
import CodeGeneration.DataFlow.Operations.Operation;

public class A_plus_B_mult_B extends Block {
    public A_plus_B_mult_B() {
        // Gates
        this
                .addInput(GateId.A, new Gate())
                .addInput(GateId.A, new Gate())
                .addOutput(GateId.C, new Gate());

        // Blueprint
        Operation add = new Addition();
        this.connectTo(add, GateId.A, GateId.A);
        this.connectTo(add, GateId.B, GateId.B);

        Operation mult = new Multiplication();
        add.connectTo(mult, GateId.C, GateId.A);
        this.connectTo(mult, GateId.B, GateId.B);

        mult.connectTo(this, GateId.C, GateId.C);
    }
}

