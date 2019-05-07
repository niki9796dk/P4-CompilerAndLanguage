package SemanticAnalysis;


import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;
import SemanticAnalysis.Exceptions.SemanticProblemException;
import SymbolTableImplementation.Scope;
import SymbolTableImplementation.SymbolTableInterface;
import TypeChecker.TypeSystem;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class FlowChecker {

    // Fields
    private TypeSystem typeSystem;
    private ArrayList<String> connected;
    private SymbolTableInterface symbolTableInterface;

    // Constructor
    public FlowChecker(SymbolTableInterface symbolTableInterface, TypeSystem typeSystem) {
        this.connected = new ArrayList<>();
        this.symbolTableInterface = symbolTableInterface;
        this.typeSystem = typeSystem;
    }

    public void check(String currentBlockScope, String currentSubScope) {
        List<String> myChannels = this.getAllChannelsOfBlock(currentBlockScope, currentSubScope);

        evaluate(myChannels, this.connected);
    }

    // Gets all channels in the channeldeclaration subscope
    private List<String> getAllChannelsOfBlock(String currentBlockScope, String currentSubScope) {
        // Get all channels
        Scope channelDeclarationScope = symbolTableInterface
                .getBlockScope(currentBlockScope)
                .getChannelDeclarationScope();

        ArrayList<String> myChannels = new ArrayList<>();

        // For all channel nodes add the channel id to the list
        for (AbstractNode node = channelDeclarationScope.getNode().getChild(); node != null; node = node.getSib()) {
            String prefix = channelPrefix(node, currentBlockScope, currentSubScope);
            myChannels.add(prefix + ((NamedIdNode) node).getId());
        }

        return myChannels;
    }

    // Figure out what kind of channel it is
    public String channelPrefix(AbstractNode node, String currentBlockScope, String currentSubScope) {
        String prefix;
        if (this.typeSystem.getSuperTypeOfNode(node, currentBlockScope, currentSubScope).equals(NodeEnum.CHANNEL_IN_MY)) {
            prefix = "IN_";
        } else {
            prefix = "OUT_";
        }
        return prefix;
    }

    // Evaluate that all channels are connected
    private void evaluate(List<String> myChannels, List<String> usedChannels) {

        // For all channels
        for (String myChannel : myChannels) {
            int amount = 0;

            // For all connections
            for (String usedChannel : usedChannels) {
                if (myChannel.equals(usedChannel)) {
                    // Count number of connections with that channel
                    amount++;
                }
            }

            // If a channel was not connected
            if (amount == 0 || (myChannel.startsWith("OUT_") && amount > 1)) {
                throw new SemanticProblemException("Incorrect use of " + myChannel + ". Amount: " + amount);
            }
        }
    }

    // Getters
    public ArrayList<String> getConnected() {
        return connected;
    }
}
