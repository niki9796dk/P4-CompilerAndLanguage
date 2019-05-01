package CodeGeneration.DataFlow.Operations;

import CodeGeneration.DataFlow.Network.Block;
import CodeGeneration.DataFlow.Network.GateId;
import CodeGeneration.DataFlow.Network.Interfaces.Gates;
import CodeGeneration.DataFlow.Network.Interfaces.Operations;

public abstract class Operation extends Block implements Operations {
    protected float value;

    @Override
    public float getValue() {
        return this.value;
    }

    @Override
    public void acceptReadySignal() {
        if (isReady()) {                         // If all inputs are ready
            this.performOperation();            // Then perform the operation
            this.getOutputGate().signalReady(); // And signal that the output gate now is ready.
        }
    }

    abstract Gates getOutputGate();

    private boolean isReady() {
        boolean isReady = true;

        for (Gates inputGate : this.inputGates.values()) {
            isReady = isReady && inputGate.isReady();
        }

        return isReady;
    }

    protected float getInputValue(GateId gateId) {
        return this.getGate(gateId).getValue();
    }
}
