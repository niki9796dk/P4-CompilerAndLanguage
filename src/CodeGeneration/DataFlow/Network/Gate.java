package CodeGeneration.DataFlow.Network;

import CodeGeneration.DataFlow.Network.Interfaces.Gates;
import CodeGeneration.DataFlow.Network.Interfaces.Operations;
import CodeGeneration.DataFlow.Network.Interfaces.SignalNodes;

import java.util.ArrayList;
import java.util.List;

public class Gate implements Gates {
    private boolean ready = false;
    private SignalNodes source;
    private List<SignalNodes> targets = new ArrayList<>();

    public void setSource(SignalNodes gate){
        this.source = gate;
    }

    public void addTarget(SignalNodes gate){
        this.targets.add(gate);
    }

    @Override
    public float getValue() {
        return this.source.getValue();
    }

    @Override
    public boolean isReady() {
        return this.ready;
    }

    @Override
    public void signalReady() {
        for (SignalNodes target : this.targets) {
            target.acceptReadySignal();
        }
    }

    @Override
    public void acceptReadySignal() {
        this.ready = true;  // Store ready state for later recall
        this.signalReady(); // Signal all outputs that their input is now ready.
    }
}
