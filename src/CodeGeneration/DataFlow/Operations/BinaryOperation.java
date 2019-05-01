package CodeGeneration.DataFlow.Operations;

import CodeGeneration.DataFlow.Network.Gate;
import CodeGeneration.DataFlow.Network.GateId;
import CodeGeneration.DataFlow.Network.Interfaces.Gates;

abstract class BinaryOperation extends Operation {
    BinaryOperation() {
        // Define inputs
        Gates A = new Gate();
        Gates B = new Gate();
        A.addTarget(this);
        B.addTarget(this);

        // Define outputs
        Gates C = new Gate();
        C.setSource(this);

        // Store gates
        this.inputGates.put(GateId.A, A);
        this.inputGates.put(GateId.B, B);

        this.outputGates.put(GateId.C, C);
    }

    @Override
    Gates getOutputGate() {
        return this.getGate(GateId.C);
    }

    @Override
    public void performOperation() {
        float a = this.getInputValue(GateId.A);
        float b = this.getInputValue(GateId.B);

        this.value = operation(a, b);
    }

    abstract float operation(float a, float b);
}
