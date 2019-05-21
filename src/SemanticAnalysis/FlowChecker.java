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

    public void addBuild(BuildNode buildInstance, String currentSubScope){
        String builtThingId = ((NamedIdNode) typeSystem.followNode(buildInstance, currentBlockId, currentSubScope)).getId();
        BlockScope blockBuilt = typeSystem.getSymbolTable().getBlockScope(builtThingId);
        boolean isSource = typeSystem.getSymbolTable().isPredefinedSource(builtThingId);
        boolean isOperation = typeSystem.getSymbolTable().isPredefinedOperation(builtThingId);

        if (isSource){
            // Handle Source // TODO: This is a bit hacky
            inConnectPoints.add(buildInstance + "out");
        } else if (isOperation){
            // Handle Operation
            // TODO: VERY hacky!
            inConnectPoints.add(buildInstance + "out");
            outConnectPoints.add(buildInstance + "A");

            if (!builtThingId.equals("_Sigmoid")){
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

    @SuppressWarnings("Duplicates")
    private void chainLink(NamedNode chainChildWithSib, String currentSubScope){
        NamedIdNode followToBuild = ((NamedIdNode) typeSystem.followNodeToBuild(chainChildWithSib, currentBlockId, currentSubScope));
        NamedIdNode followToBase = ((NamedIdNode) typeSystem.followNode(chainChildWithSib, currentBlockId, currentSubScope));
        boolean isThis = ("this").equals(followToBase.getId());
        boolean isSource = typeSystem.getSymbolTable().isPredefinedSource(followToBase.getId());
        boolean isOperation = typeSystem.getSymbolTable().isPredefinedOperation(followToBase.getId());
        String inString;
        String outString;

        System.out.println("RRRRRRR: " + followToBuild);

        if (isThis){
            // "this" case
            inString = currentBlockId + ((NamedIdNode) followToBase.getChild()).getId();

        } else if (isSource || isOperation) {
            // source/operation case
            // TODO: Even more Operation and Source mess
            inString = followToBuild.toString()
                    + "out";

        } else if (followToBase.getNodeEnum().equals(NodeEnum.SELECTOR)
                && followToBase.getChild() != null) {
            // pipe.channel case
            String blockInString = follow_SelectorDotSelectorSafe(followToBuild, currentSubScope).toString();

            inString = blockInString + ((NamedIdNode) followToBase.getChild()).getId();

        } else if (followToBase.getNodeEnum().equals(NodeEnum.CHANNEL_IN_MY) || followToBase.getNodeEnum().equals(NodeEnum.CHANNEL_OUT_MY)) {
            inString = currentBlockId + followToBase.getId();

        } else {
            // channel variable case
            System.out.println("HMMM: " + followToBuild);
            inString = followToBuild.toString()
                    + typeSystem.getSymbolTable()
                    .getBlockScope(followToBase.getId())
                    .getChannelDeclarationScope().getNode()
                    .findFirstChildOfClass(MyOutChannelNode.class).getId();
        }

        // When giving to chainLinkOut, it actually needs to be chainChildWithSib and NOT value!
        outString = chainLinkOut((NamedNode) chainChildWithSib.getSib(), currentSubScope);
        addConnections(inString, outString);
    }

    private String chainLinkOut(NamedNode outPartOfChain, String currentSubScope){
        NamedIdNode followToBuild = ((NamedIdNode) typeSystem.followNodeToBuild(outPartOfChain, currentBlockId, currentSubScope));
        NamedIdNode followToBase = ((NamedIdNode) typeSystem.followNode(followToBuild, currentBlockId, currentSubScope));
        boolean isThis = ("this").equals(followToBase.getId());

        if (isThis){
            // "this" case
            return currentBlockId + ((NamedIdNode) followToBase.getChild()).getId();

        } else if (followToBase.getNodeEnum().equals(NodeEnum.BUILD)) {
            // pipe case
            String idPortion;

            if (typeSystem.getSymbolTable().isPredefinedSource(followToBase.getId())){
                // Source case
                throw new ShouldNotHappenException("A chain entering a Source??");
            } else if (typeSystem.getSymbolTable().isPredefinedOperation(followToBase.getId())){
                // Operation case
                idPortion = "A";
            } else {
                // Block case
                 idPortion = typeSystem.getSymbolTable().getBlockScope(followToBase.getId()).getChannelDeclarationScope().getNode().findFirstChildOfClass(MyInChannelNode.class).getId();
            }

            return followToBuild + idPortion;

        } else if (followToBase.getNodeEnum().equals(NodeEnum.SELECTOR) && followToBase.getChild() != null) {
            // pipe.channel case
            String blockInString = follow_SelectorDotSelectorSafe(followToBuild, currentSubScope).toString();
            return blockInString + ((NamedIdNode) followToBase.getChild()).getId();

        } else if (followToBase.getNodeEnum().equals(NodeEnum.CHANNEL_IN_MY) || followToBase.getNodeEnum().equals(NodeEnum.CHANNEL_OUT_MY)) {
            return currentBlockId + followToBase.getId();

        } else {
            // channel variable case
            return followToBuild.toString()
                    + typeSystem.getSymbolTable().getBlockScope(followToBase.getId())
                    .getChannelDeclarationScope()
                    .getNode().findFirstChildOfClass(MyInChannelNode.class).getId();
        }
    }


    /**
     */
    private void addAllChannelsOfBlockInstance(BuildNode instance, BlockScope theBlock) {
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
        AbstractNode affasffs = typeSystem.followNodeToBuild(node, currentBlockId, currentSubScope);

        if (affasffs instanceof SelectorNode){
            SelectorNode dummy = new SelectorNode(((SelectorNode) affasffs).getId());
            dummy.setNumber(((SelectorNode) affasffs).getNumber());

            affasffs = typeSystem.followNodeToBuild(dummy, currentBlockId, currentSubScope);
        }

        return affasffs;
    }

    @SuppressWarnings("Duplicates")
    private void linkFromGroupMember(AbstractNode in, String out, String currentSubScope){
        NamedIdNode followedToBuild = (NamedIdNode) typeSystem.followNodeToBuild(in, currentBlockId, currentSubScope);
        NamedIdNode followedToBase = (NamedIdNode) typeSystem.followNode(in, currentBlockId, currentSubScope);
        boolean isSource = typeSystem.getSymbolTable().isPredefinedSource(followedToBase.getId());
        boolean isOperation = typeSystem.getSymbolTable().isPredefinedOperation(followedToBase.getId());
        String inString;

        if (followedToBuild.getNodeEnum().equals(NodeEnum.SELECTOR)){
            if (followedToBase.getId().equals("this")){
                // this. case
                inString = currentBlockId + ((NamedIdNode) followedToBase.getChild()).getId();

            } else {
                // var. case
                SelectorNode dummy = new SelectorNode(followedToBase.getId());
                dummy.setNumber(followedToBase.getNumber());

                inString = typeSystem.followNodeToBuild(dummy, currentBlockId, currentSubScope) + ((NamedIdNode) followedToBase.getChild()).getId();

            }

        } else if (isSource || isOperation){
            // Source, Operation case
            inString = followedToBuild + "out";

        } else if (followedToBase.getNodeEnum().equals(NodeEnum.CHANNEL_OUT_MY) || followedToBuild.getNodeEnum().equals(NodeEnum.CHANNEL_IN_MY)){
            // Own channel case
            inString = currentBlockId + followedToBuild.getId();

        } else {
            // Block case
            inString = followedToBuild + typeSystem.getSymbolTable()
                    .getBlockScope(followedToBase.getId())
                    .getChannelDeclarationScope().getNode()
                    .findFirstChildOfClass(MyOutChannelNode.class).getId();
        }

        addConnections(inString, out);
    }

    private Iterator<String> getInChannelIdsOfNode(AbstractNode node, String currentSubScope) {
        NamedIdNode followedToBuild = ((NamedIdNode) typeSystem.followNodeToBuild(node, currentBlockId, currentSubScope));
        NamedIdNode followedToBase = ((NamedIdNode) typeSystem.followNode(followedToBuild, currentBlockId, currentSubScope));
        boolean isSource = typeSystem.getSymbolTable().isPredefinedSource(followedToBase.getId());
        boolean isOperation = typeSystem.getSymbolTable().isPredefinedOperation(followedToBase.getId());

        List<String> inChannels = new ArrayList<>();

        if (isSource) {
            throw new ShouldNotHappenException("Chain into source??");
        } else if (isOperation) {
            // TODO: OPERATIONS!
            inChannels.add(followedToBuild + "A");
            // TODO: This check should be about nr of inputs
            if (!followedToBase.getId().equals("_Sigmoid")) {
                inChannels.add(followedToBuild + "B");
            }


        } else if (followedToBase.getNodeEnum().equals(NodeEnum.SELECTOR)) {
            // .notation case
            if (followedToBase.getId().equals("this")){
                // this. case
                inChannels.add(currentBlockId + ((NamedIdNode) followedToBase.getChild()).getId());

            } else {
                // var. case
                inChannels.add(followedToBuild + ((NamedIdNode) followedToBase.getChild()).getId());

            }

        } else {
             for(AbstractNode inChannel : typeSystem.getSymbolTable()
                    .getBlockScope(followedToBase.getId())
                    .getChannelDeclarationScope().getAllChildrenOfType(NodeEnum.CHANNEL_IN_MY)){
                 inChannels.add(followedToBuild + ((NamedIdNode) inChannel).getId());
            }
        }

        return inChannels.iterator();
    }

    public String getCurrentBlockId() {
        return currentBlockId;
    }

    private void addConnections(String in, String out){
        inConnections.add(in);
        outConnections.add(out);
    }
}
