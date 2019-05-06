package SemanticAnalysis;


import AST.Nodes.AbstractNodes.Nodes.AbstractNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;
import SemanticAnalysis.Exceptions.SemanticProblemException;
import SymbolTableImplementation.Scope;
import SymbolTableImplementation.SymbolTableInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class FlowChecker {

    // Fields
    private ArrayList<String> connected;
    private SymbolTableInterface symbolTableInterface;

    // Constructors
    public FlowChecker(SymbolTableInterface symbolTableInterface) {
        this.connected = new ArrayList<>();
        this.symbolTableInterface = symbolTableInterface;
    }

    public void check(String currentBlockScope) {
        List<String> myChannels = this.getAllChannelsOfBlock(currentBlockScope);

        System.out.println("Connected:");
        System.out.println(this.connected.stream().map(String::toString).collect(Collectors.joining("\n")));

        System.out.println("MyChannels:");
        System.out.println(myChannels.stream().map(String::toString).collect(Collectors.joining("\n")));


        evaluate(myChannels, this.connected);
    }

    private List<String> getAllChannelsOfBlock(String currentBlockScope) {
        // Get all channels
        Scope channelDeclarationScope = symbolTableInterface
                .getBlockScope(currentBlockScope)
                .getChannelDeclarationScope();

        ArrayList<String> myChannels = new ArrayList<>();

        // For all channel nodes add the channel id to the list
        for (AbstractNode node = channelDeclarationScope.getNode().getChild(); node != null; node = node.getSib()) {
            myChannels.add(((NamedIdNode) node).getId());
        }

        return myChannels;
    }

    private void evaluate(List<String> myChannels, List<String> usedChannels) {
        for (String myChannel : myChannels) {
            int amount = 0;

            for (String usedChannel : usedChannels) {
                if (myChannel.equals(usedChannel)) {
                    amount++;
                }
            }

            if (amount == 0) {
                throw new SemanticProblemException("Channel " + myChannel + " was never used!");
            }
        }
    }

    // Getters
    public ArrayList<String> getConnected() {
        return connected;
    }
}
