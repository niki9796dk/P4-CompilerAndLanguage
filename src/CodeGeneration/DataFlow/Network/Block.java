package CodeGeneration.DataFlow.Network;

import CodeGeneration.DataFlow.Network.Interfaces.Blocks;
import CodeGeneration.DataFlow.Network.Interfaces.Gates;

import java.util.HashMap;
import java.util.Map;

public abstract class Block implements Blocks {
    private Map<GateId, Gates> inputGates = new HashMap<>(2);
    private Map<GateId, Gates> outputGates = new HashMap<>(1);


    /**
     * Add an input gate
     * @param id Desired id of the new gate.
     * @param g The gate
     * @return  a reference to this object.
     */
    public Block addInput(GateId id, Gates g){
        this.inputGates.put(id,g);
        return this;
    }

    /**
     * Add an output gate
     * @param id Desired id of the new gate.
     * @param g The gate
     * @return  a reference to this object.
     */
    public Block addOutput(GateId id, Gates g){
        this.outputGates.put(id,g);
        return this;
    }

    @Override
    public void connectTo(Blocks toBlock, GateId fromGate, GateId toGate) {
        Gates outputGate = this.outputGates.getOrDefault(fromGate,
                this.inputGates.getOrDefault(fromGate, null));

        Gates targetGate = toBlock.getGate(toGate); // TODO: Null check?

        outputGate.addTarget(targetGate);
        targetGate.setSource(outputGate);
    }

    @Override
    public Gates getGate(GateId gateId) {
        if (this.inputGates.containsKey(gateId)){
            return this.inputGates.get(gateId);
        } else if (this.outputGates.containsKey(gateId)) {
            return this.outputGates.get(gateId);
        } else {
            throw new IllegalArgumentException("No such gate: " + gateId);
        }
    }
}
