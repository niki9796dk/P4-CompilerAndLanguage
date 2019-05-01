package SemanticAnalysis;

import java.util.ArrayList;

public class FlowChecker {

    private ArrayList<String> channels;

    public FlowChecker() {
        this.channels = new ArrayList<>();
    }

    public FlowChecker(ArrayList<String> channels) {
        this.channels = channels;
    }

    // Get all blocks

    // Get all connections

    // Make sure every channel has been accounted for.

    public ArrayList<String> getChannels() {
        return channels;
    }
}
