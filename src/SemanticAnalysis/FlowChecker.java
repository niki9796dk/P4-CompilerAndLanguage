package SemanticAnalysis;

import SemanticAnalysis.Exceptions.UnusedChannelException;

import java.util.ArrayList;

public class FlowChecker {

    // Fields
    private ArrayList<String> channels;
    private ArrayList<String> connected;

    // Constructors
    public FlowChecker() {
        this.channels = new ArrayList<>();
    }

    public FlowChecker(ArrayList<String> channels) {
        this.channels = channels;
    }

    public boolean check() throws UnusedChannelException {
        // that all channels are used
        for (String channel : channels) {
            if (!connected.contains(channel)) {
                throw new UnusedChannelException();
            }
        }

        // that a channel only is used once

        return true;
    }

    // Getters
    public ArrayList<String> getChannels() {
        return channels;
    }

    public ArrayList<String> getConnected() {
        return connected;
    }
}
