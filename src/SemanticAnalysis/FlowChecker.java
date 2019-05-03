package SemanticAnalysis;


import AST.Nodes.AbstractNodes.Nodes.AbstractNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;
import SymbolTableImplementation.Scope;
import SymbolTableImplementation.SymbolTableInterface;

import java.util.ArrayList;
import java.util.List;

public class FlowChecker {

    // Fields
    private ArrayList<String> connected;
    private SymbolTableInterface symbolTableInterface;

    // Constructors
    public FlowChecker(SymbolTableInterface symbolTableInterface) {
        this.connected = new ArrayList<>();
        this.symbolTableInterface = symbolTableInterface;
    }

    public boolean check(String currentBlockScope) {
        List<String> myChannels = getAllChannelsOfBlock(currentBlockScope);

        return evaluate(myChannels, this.connected);
    }

    private List<String> getAllChannelsOfBlock(String currentBlockScope) {
        // Get all channels
        Scope channelDeclarationScope = symbolTableInterface
                .getBlockScope(currentBlockScope)
                .getChannelDeclarationScope();

        ArrayList<String> myChannels = new ArrayList<>();

        // For all channel nodes add the channel id to the list
        for (AbstractNode node = channelDeclarationScope.getNode().getChild(); node == null; node = node.getSib()) {
            myChannels.add(((NamedIdNode) node).getId());
        }

        return myChannels;
    }

    private boolean evaluate(List<String> myChannels, List<String> usedChannels) {
        for (String myChannel : myChannels) {
            int amount = 0;

            for (String usedChannel : usedChannels) {
                if (myChannel.equals(usedChannel)) {
                    amount++;
                    if (amount != 0) {
                        return false;
                    }
                }
            }

        }
        return true;
    }

    // Getters
    public ArrayList<String> getConnected() {
        return connected;
    }
}
