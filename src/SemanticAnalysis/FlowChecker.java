package SemanticAnalysis;


import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;
import SemanticAnalysis.Exceptions.IncorrectChannelUsageException;
import SemanticAnalysis.Exceptions.SemanticProblemException;
import SymbolTableImplementation.Scope;
import SymbolTableImplementation.SymbolTableInterface;
import TypeChecker.TypeSystem;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * The FlowChecker class - Is used as a helper class for performing flow checking in the semantic analysis phase
 */
public class FlowChecker {

    // Fields
    private TypeSystem typeSystem;
    private ArrayList<String> connected;
    private SymbolTableInterface symbolTableInterface;

    /**
     * The constructor
     * @param symbolTableInterface A symbole table interface object.
     */
    public FlowChecker(SymbolTableInterface symbolTableInterface) {
        this.connected = new ArrayList<>();
        this.symbolTableInterface = symbolTableInterface;
        this.typeSystem = new TypeSystem(symbolTableInterface);
    }

    /**
     * Will verify that the collected information about channel usages is sufficient for actual data flow.
     * @param currentBlockScope The current block scope to compare from
     * @param currentSubScope The current sub scope to compare from
     */
    public void check(String currentBlockScope, String currentSubScope) {
        List<String> myChannels = this.getAllChannelsOfBlock(currentBlockScope, currentSubScope);

        evaluate(myChannels, this.connected);
    }

    /**
     * Get's all channels in the channel declaration subscope
     * @param currentBlockScope The current block scope to compare from
     * @param currentSubScope The current sub scope to compare from
     * @return A list of all channels, in string representation, of the given block.
     */
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

    /**
     * Figure out what kind of channel it is, and return the appropriate channel prefix
     * @param node The channel node to check
     * @param currentBlockScope The current block scope
     * @param currentSubScope The current sub scope
     * @return An appropriate channel prefix for the channel node.
     */
    public String channelPrefix(AbstractNode node, String currentBlockScope, String currentSubScope) {
        if (this.typeSystem.getSuperTypeOfNode(node, currentBlockScope, currentSubScope).equals(NodeEnum.CHANNEL_IN_MY)) {
            return  "IN_";
        } else {
            return  "OUT_";
        }
    }

    /**
     * Evaluate that all channels are connected, and in allowed counts.
     * @param myChannels A list of all mychannels:in/out of a block
     * @param usedChannels A list of all registered used channels
     */
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
                throw new IncorrectChannelUsageException("Incorrect use of " + myChannel + ". Amount: " + amount);
            }
        }
    }

    /**
     * A getter for the list of all registered connections
     * @return The list of known connections.
     */
    public ArrayList<String> getConnected() {
        return connected;
    }
}
