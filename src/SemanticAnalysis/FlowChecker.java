package SemanticAnalysis;


import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;
import AST.Nodes.NodeClasses.NamedNodes.ChainNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.BuildNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.MyInChannelNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.MyOutChannelNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.SelectorNode;
import SemanticAnalysis.Exceptions.IncorrectChannelUsageException;
import SymbolTableImplementation.BlockScope;
import SymbolTableImplementation.Scope;
import SymbolTableImplementation.SymbolTableInterface;
import TypeChecker.Exceptions.ShouldNotHappenException;
import TypeChecker.TypeSystem;

import java.util.*;

/**
 * The FlowChecker class - Is used as a helper class for performing flow checking in the semantic analysis phase
 */
public class FlowChecker {

    // Fields
    private static Stack<FlowChecker> flowStack;
    private static TypeSystem typeSystem;
    private Set<String> inConnectPoints;
    private Set<String> outConnectPoints;
    private List<String> inConnections;
    private List<String> outConnections;
    private String currentBlockId;


    public FlowChecker(String currentBlockScope) {
        this.currentBlockId = currentBlockScope;
        this.inConnectPoints = new HashSet<>();
        this.outConnectPoints = new HashSet<>();
        this.inConnections = new ArrayList<>();
        this.outConnections = new ArrayList<>();
    }

    public static void setup(SymbolTableInterface symbolTableInterface){
        typeSystem = new TypeSystem(symbolTableInterface);
        flowStack = new Stack<>();
    }

    public static FlowChecker startBlock(String currentBlockScope){
        FlowChecker flow = new FlowChecker(currentBlockScope);

        flowStack.push(flow);
        flow.addAllChannelsOfBlock_My();
        return flow;
    }

    public FlowChecker evaluateBlock(){
        // This is really useful for debugging.
        int i = 0;
        for (String s : inConnections){
            System.out.print(inConnections.get(i) + " - " + outConnections.get(i) + " :::: ");
            i++;
        }
        System.out.println(System.lineSeparator() + inConnectPoints);
        System.out.println(outConnectPoints);

        for (String connectedOut : outConnections){
            boolean firstTimeInConnected = outConnectPoints.remove(connectedOut);

            if (!firstTimeInConnected){
                throw new IncorrectChannelUsageException("In channel " + connectedOut + " connected at multiple points.");
            }
        }

        for (String connectedIn : inConnections){
            inConnectPoints.remove(connectedIn);
        }

        if (!inConnectPoints.isEmpty() || !outConnectPoints.isEmpty()){
            throw new IncorrectChannelUsageException("Not all channel connection points in " + currentBlockId + " are used:"
                    + System.lineSeparator() + inConnectPoints
                    + System.lineSeparator() + outConnectPoints);
        }

        flowStack.pop();
        return flowStack.empty() ? null : flowStack.peek();
    }

    public void addBuild(NamedIdNode buildInstance, String currentSubScope){
        buildInstance = (NamedIdNode) typeSystem.followNode(buildInstance, currentBlockId, currentSubScope);
        String builtThing = typeSystem.getSubTypeOfNode(buildInstance, currentBlockId, currentSubScope);
        BlockScope blockBuilt = typeSystem.getSymbolTable().getBlockScope(builtThing);
        boolean isSource = typeSystem.getSymbolTable().isPredefinedSource(builtThing);
        boolean isOperation = typeSystem.getSymbolTable().isPredefinedOperation(builtThing);

        if (isSource){
            // Handle Source // TODO: This is a bit hacky
            inConnectPoints.add(buildInstance + "out");
        } else if (isOperation){
            // Handle Operation
            // TODO: VERY hacky!
            inConnectPoints.add(buildInstance + "out");
            outConnectPoints.add(buildInstance + "A");

            if (!builtThing.equals("_Sigmoid")){
                outConnectPoints.add(buildInstance + "B");
            }

        } else if (blockBuilt != null){
            // Handle Block
            addAllChannelsOfBlockInstance(buildInstance, blockBuilt);
        } else {
            throw new ShouldNotHappenException("Can we build things that aren't blocks, operations or sources??");
        }
    }

    public void addChain(ChainNode chain, String currentSubScope){
        boolean isGroupChain = ((NamedNode) chain.getChild()).getNodeEnum().equals(NodeEnum.GROUP);

        if (isGroupChain){
            // Group case
            AbstractNode chainSecondElement = chain.getChild().getSib();
            Iterator<String> secondElementInputs = getInChannelIdsOfNode(chainSecondElement, currentSubScope);

            for (AbstractNode member = chain.getChild().getChild(); member != null; member = member.getSib()) {
                linkFromGroupMember(member, secondElementInputs.next(), currentSubScope);
            }

            for (AbstractNode child = chainSecondElement; child.getSib() != null; child = child.getSib()) {
                chainLink((NamedNode) child, currentSubScope);
            }

        } else {
            // Plain chain case
            for (AbstractNode child = chain.getChild(); child.getSib() != null; child = child.getSib()) {
                chainLink((NamedNode) child, currentSubScope);
            }
        }
    }

    private void chainLink(NamedNode chainChildWithSib, String currentSubScope){
        NamedIdNode value = ((NamedIdNode) typeSystem.followNode(chainChildWithSib, currentBlockId, currentSubScope));
        boolean isThis = ("this").equals(value.getId());
        boolean isSource = typeSystem.getSymbolTable().isPredefinedSource(value.getId());
        boolean isOperation = typeSystem.getSymbolTable().isPredefinedOperation(value.getId());
        String inString;
        String outString;

        if (isThis){
            // "this" case
            inString = currentBlockId + ((NamedIdNode) value.getChild()).getId();
            // When giving to chainLinkOut, it actually needs to be chainChildWithSib and NOT value!
            outString = chainLinkOut((NamedNode) chainChildWithSib.getSib(), currentSubScope);
            addConnections(inString, outString);

        } else if (isSource || isOperation) {
            // pipe case
            // TODO: Even more Operation and Source mess
            inString = value.toString()
                    + "out";
            outString = chainLinkOut((NamedNode) chainChildWithSib.getSib(), currentSubScope);
            addConnections(inString, outString);

        } else if (value.getNodeEnum().equals(NodeEnum.SELECTOR)
                && value.getChild() != null) {
            // pipe.channel case
            String blockInString = follow_SelectorDotSelectorSafe(value, currentSubScope).toString();

            inString = blockInString + ((NamedIdNode) value.getChild()).getId();
            outString = chainLinkOut((NamedIdNode) chainChildWithSib.getSib(), currentSubScope);
            addConnections(inString, outString);

        } else {
            // channel variable case
            inString = value.toString()
                    + typeSystem.getSymbolTable()
                    .getBlockScope(value.getId())
                    .getChannelDeclarationScope().getNode()
                    .findFirstChildOfClass(MyOutChannelNode.class).getId();
            outString = chainLinkOut((NamedNode) chainChildWithSib.getSib(), currentSubScope);
            addConnections(inString, outString);
        }
    }

    private String chainLinkOut(NamedNode outPartOfChain, String currentSubScope){
        NamedIdNode value = (NamedIdNode) typeSystem.followNode(outPartOfChain, currentBlockId, currentSubScope);
        boolean isThis = ("this").equals(value.getId());

        if (isThis){
            // "this" case
            return currentBlockId + ((NamedIdNode) value.getChild()).getId();

        } else if (value.getNodeEnum().equals(NodeEnum.BUILD)) {
            // pipe case
            String idPortion;

            if (typeSystem.getSymbolTable().isPredefinedSource(value.getId())){
                // Source case
                throw new ShouldNotHappenException("A chain entering a Source??");
            } else if (typeSystem.getSymbolTable().isPredefinedOperation(value.getId())){
                // Operation case
                idPortion = "A";
            } else {
                // Block case
                 idPortion = typeSystem.getSymbolTable().getBlockScope(value.getId()).getChannelDeclarationScope().getNode().findFirstChildOfClass(MyInChannelNode.class).getId();
            }

            return value + idPortion;

        } else if (value.getNodeEnum().equals(NodeEnum.SELECTOR) && value.getChild() != null) {
            // pipe.channel case
            String blockInString = follow_SelectorDotSelectorSafe(value, currentSubScope).toString();
            return blockInString + ((NamedIdNode) value.getChild()).getId();

        } else if (value.getNodeEnum().equals(NodeEnum.CHANNEL_IN_MY) || value.getNodeEnum().equals(NodeEnum.CHANNEL_OUT_MY)) {
            return currentBlockId + value.getId();

        } else {
            // channel variable case
            return value.toString()
                    + typeSystem.getSymbolTable().getBlockScope(value.getId())
                    .getChannelDeclarationScope()
                    .getNode().findFirstChildOfClass(MyInChannelNode.class).getId();
        }
    }


    /**
     */
    private void addAllChannelsOfBlockInstance(NamedIdNode instance, BlockScope theBlock) {
        // Get all channels
        Scope channelDeclarationScope = theBlock.getChannelDeclarationScope();

        // For all channel nodes add the channel id to the list
        for (NamedIdNode node = (NamedIdNode) channelDeclarationScope.getNode().getChild(); node != null; node = (NamedIdNode) node.getSib()) {
            switch (node.getNodeEnum()){
                case CHANNEL_IN_MY:
                    outConnectPoints.add(instance + node.getId());
                    break;
                case CHANNEL_OUT_MY:
                    inConnectPoints.add(instance + node.getId());
                    break;
                default:
                    throw new ShouldNotHappenException("A non-mychannel in the channel scope?? " + instance.getId() + " - " + node);
            }
        }
    }

    // Variant of the
    private void addAllChannelsOfBlock_My() {
        // Get all channels
        Scope channelDeclarationScope = typeSystem.getSymbolTable()
                .getBlockScope(currentBlockId)
                .getChannelDeclarationScope();

        // For all channel nodes add the channel id to the list
        for (NamedIdNode node = (NamedIdNode) channelDeclarationScope.getNode().getChild(); node != null; node = (NamedIdNode) node.getSib()) {
            switch (node.getNodeEnum()){
                case CHANNEL_IN_MY:
                    inConnectPoints.add(currentBlockId + node.getId());
                    break;
                case CHANNEL_OUT_MY:
                    outConnectPoints.add(currentBlockId + node.getId());
                    break;
                default:
                    throw new ShouldNotHappenException("A non-mychannel in the channel scope?? " + currentBlockId + node);
            }
        }
    }

    private AbstractNode follow_SelectorDotSelectorSafe(AbstractNode node, String currentSubScope){
        AbstractNode affasffs = typeSystem.followNode(node, currentBlockId, currentSubScope);

        if (affasffs instanceof SelectorNode){
            SelectorNode dummy = new SelectorNode(((SelectorNode) affasffs).getId());
            dummy.setNumber(((SelectorNode) affasffs).getNumber());

            affasffs = typeSystem.followNode(dummy, currentBlockId, currentSubScope);
        }

        return affasffs;
    }

    private void linkFromGroupMember(AbstractNode in, String out, String currentSubScope){
        NamedIdNode followedIn = (NamedIdNode) typeSystem.followNode(in, currentBlockId, currentSubScope);
        boolean isSource = typeSystem.getSymbolTable().isPredefinedSource(followedIn.getId());
        boolean isOperation = typeSystem.getSymbolTable().isPredefinedOperation(followedIn.getId());
        String inString;

        if (followedIn.getNodeEnum().equals(NodeEnum.SELECTOR)){
            if (followedIn.getId().equals("this")){
                // this. case
                inString = currentBlockId + ((NamedIdNode) followedIn.getChild()).getId();

            } else {
                // var. case
                SelectorNode dummy = new SelectorNode(followedIn.getId());
                dummy.setNumber(followedIn.getNumber());

                inString = typeSystem.followNode(dummy, currentBlockId, currentSubScope) + ((NamedIdNode) followedIn.getChild()).getId();

            }
        } else if (isSource || isOperation){
            // Source, Operation case
            inString = followedIn + "out";

        } else if (followedIn.getNodeEnum().equals(NodeEnum.CHANNEL_OUT_MY) || followedIn.getNodeEnum().equals(NodeEnum.CHANNEL_IN_MY)){
            // Own channel case
            inString = currentBlockId + followedIn.getId();

        } else {
            // Block case
            inString = followedIn + typeSystem.getSymbolTable()
                    .getBlockScope(followedIn.getId())
                    .getChannelDeclarationScope().getNode()
                    .findFirstChildOfClass(MyOutChannelNode.class).getId();

        }

        addConnections(inString, out);
    }

    private Iterator<String> getInChannelIdsOfNode(AbstractNode secondElement, String currentSubScope) {
        NamedIdNode followedSeconElement = ((NamedIdNode) typeSystem.followNode(secondElement, currentBlockId, currentSubScope));
        boolean isSource = typeSystem.getSymbolTable().isPredefinedSource(followedSeconElement.getId());
        boolean isOperation = typeSystem.getSymbolTable().isPredefinedOperation(followedSeconElement.getId());

        List<String> secondElementInChannels = new ArrayList<>();

        if (isSource) {
            throw new ShouldNotHappenException("Chain into source??");
        } else if (isOperation) {
            // TODO: OPERATIONS!
            secondElementInChannels.add(followedSeconElement + "A");
            // TODO: This check should be about nr of inputs
            if (!followedSeconElement.getId().equals("_Sigmoid")) {
                secondElementInChannels.add(followedSeconElement + "B");
            }

        } else if (followedSeconElement.getNodeEnum().equals(NodeEnum.SELECTOR)) {
            // .notation case
            if (followedSeconElement.getId().equals("this")){
                // this. case
                secondElementInChannels.add(currentBlockId + ((NamedIdNode) followedSeconElement.getChild()).getId());

            } else {
                // var. case
                secondElementInChannels.add(followedSeconElement + ((NamedIdNode) followedSeconElement.getChild()).getId());

            }

        } else {
             for(AbstractNode ab : typeSystem.getSymbolTable()
                    .getBlockScope(followedSeconElement.getId())
                    .getChannelDeclarationScope().getAllChildrenOfType(NodeEnum.CHANNEL_IN_MY)){
                 secondElementInChannels.add(secondElement + ((NamedIdNode) ab).getId());
            }
        }

        return secondElementInChannels.iterator();
    }

    public String getCurrentBlockId() {
        return currentBlockId;
    }

    private void addConnections(String in, String out){
        inConnections.add(in);
        outConnections.add(out);
    }
}
