package SemanticAnalysis;


import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;
import AST.Nodes.NodeClasses.NamedNodes.ChainNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.*;
import CompilerExceptions.SemanticExceptions.IncorrectChannelUsageException;
import CompilerExceptions.TypeExceptions.ShouldNotHappenException;
import SemanticAnalysis.Datastructures.ProcCall;
import SymbolTableImplementation.BlockScope;
import SymbolTableImplementation.Scope;
import SymbolTableImplementation.SymbolTableInterface;
import TypeChecker.TypeSystem;
import java_cup.runtime.ComplexSymbolFactory;

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
    private Stack<ProcCall> procStack;


    private FlowChecker(String currentBlockScope) {
        this.currentBlockId = currentBlockScope;
        this.inConnectPoints = new HashSet<>();
        this.outConnectPoints = new HashSet<>();
        this.inConnections = new ArrayList<>();
        this.outConnections = new ArrayList<>();
        this.procStack = new Stack<>();
    }

    public static void setup(SymbolTableInterface symbolTableInterface) {
        typeSystem = new TypeSystem(symbolTableInterface);
        flowStack = new Stack<>();
    }

    public static FlowChecker startBlock(String currentBlockScope) {
        FlowChecker flow = new FlowChecker(currentBlockScope);

        flowStack.push(flow);
        flow.addAllChannelsOfBlock_My();
        return flow;
    }

    public FlowChecker evaluateBlock(AbstractNode node) {
        // This is really useful for debugging.
//        int i = 0;
//        for (String s : inConnections) {
//            System.out.print(inConnections.get(i) + " -> " + outConnections.get(i) + " :::: ");
//            i++;
//        }
//        System.out.println(System.lineSeparator() + inConnectPoints);
//        System.out.println(outConnectPoints);

        for (String connectedOut : outConnections) {
            boolean firstTimeInConnected = outConnectPoints.remove(connectedOut);

            if (!firstTimeInConnected) {
                throw new IncorrectChannelUsageException(node, "In channel " + connectedOut + " connected at multiple points, or not at all.");
            }
        }

        for (String connectedIn : inConnections) {
            inConnectPoints.remove(connectedIn);
        }

        if (!inConnectPoints.isEmpty() || !outConnectPoints.isEmpty()) {
            throw new IncorrectChannelUsageException(node, "Not all channel connection points in " + currentBlockId + " are used:"
                    + System.lineSeparator() + inConnectPoints
                    + System.lineSeparator() + outConnectPoints);
        }

        flowStack.pop();
        return flowStack.empty() ? null : flowStack.peek();
    }

    public void addBuild(BuildNode buildInstance, String currentSubScope) {
        String builtThingId = ((NamedIdNode) typeSystem.followNode(buildInstance, currentBlockId, currentSubScope)).getId();
        BlockScope blockBuilt = typeSystem.getSymbolTable().getBlockScope(builtThingId);
        boolean isSource = typeSystem.isPredefinedSource(builtThingId);
        boolean isOperation = typeSystem.isPredefinedOperation(builtThingId);

        if (isSource) {
            // Handle Source
            List<String> outChannelIds = typeSystem.getOperationOrSourceOutChannelIds(builtThingId);

            for (String id : outChannelIds) {
                inConnectPoints.add(buildInstance + id);
            }

        } else if (isOperation) {
            // Handle Operation
            List<String> outChannelIds = typeSystem.getOperationOrSourceOutChannelIds(builtThingId);
            List<String> inChannelIds = typeSystem.getOperationInChannelIds(builtThingId);

            for (String id : outChannelIds) {
                inConnectPoints.add(buildInstance + id);
            }
            for (String id : inChannelIds) {
                outConnectPoints.add(buildInstance + id);
            }

        } else if (blockBuilt != null) {
            // Handle Block
            addAllChannelsOfBlockInstance(buildInstance, blockBuilt);

        } else {
            throw new ShouldNotHappenException(buildInstance, "Can we build things that aren't blocks, operations or sources??");
        }
    }

    public void addChain(ChainNode chain, String currentSubScope) {
        boolean isGroupChain = ((NamedNode) chain.getChild()).getNodeEnum().equals(NodeEnum.GROUP);

        if (isGroupChain) {
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
    private void chainLink(NamedNode chainChildWithSib, String currentSubScope) {
        NamedIdNode followToBuild = ((NamedIdNode) typeSystem.followNodeToBuild(chainChildWithSib, currentBlockId, currentSubScope));
        NamedIdNode followToBase = ((NamedIdNode) typeSystem.followNode(chainChildWithSib, currentBlockId, currentSubScope));
        boolean isThis = ("this").equals(followToBase.getId());
        boolean isSource = typeSystem.isPredefinedSource(followToBase.getId());
        boolean isOperation = typeSystem.isPredefinedOperation(followToBase.getId());
        String inString;
        String outString;

        if (isThis) {
            // "this" case
            inString = currentBlockId + ((NamedIdNode) followToBase.getChild()).getId();

        } else if (isSource || isOperation) {
            // source/operation case
            List<String> outChannelIds = typeSystem.getOperationOrSourceOutChannelIds(followToBase);
            inString = followToBuild + outChannelIds.get(0);

        } else if (followToBase.getNodeEnum().equals(NodeEnum.SELECTOR)
                && followToBase.getChild() != null) {
            // pipe.channel case
            String blockInString = follow_SelectorDotSelectorSafe(followToBuild, currentSubScope).toString();

            inString = blockInString + ((NamedIdNode) followToBase.getChild()).getId();

        } else if (followToBase.getNodeEnum().equals(NodeEnum.CHANNEL_IN_MY) || followToBase.getNodeEnum().equals(NodeEnum.CHANNEL_OUT_MY)) {
            AbstractNode parameterVariable = typeSystem.followNodeToBuildOfChannel(chainChildWithSib, currentBlockId, currentSubScope);

            // The case where followToBase is a myChannel but followNodeToBuildOfChannel returns a Selector, is where we have a parameter we have to follow back to get the build statement of origin.
            if (((NamedNode) parameterVariable).getNodeEnum().equals(NodeEnum.SELECTOR) && parameterVariable.getChild() == null) {
                inString = procStack.peek().getBuildOfParameter((NamedIdNode) parameterVariable) + followToBase.getId();

            } else {
                // otherwise, it is one of the local block's myChannels
                inString = currentBlockId + followToBuild.getId();
            }

        } else {
            // channel variable case
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

    private String chainLinkOut(NamedNode outPartOfChain, String currentSubScope) {
        NamedIdNode followToBuild = ((NamedIdNode) typeSystem.followNodeToBuild(outPartOfChain, currentBlockId, currentSubScope));
        NamedIdNode followToBase = ((NamedIdNode) typeSystem.followNode(followToBuild, currentBlockId, currentSubScope));
        boolean isThis = ("this").equals(followToBase.getId());

        if (isThis) {
            // "this" case
            return currentBlockId + ((NamedIdNode) followToBase.getChild()).getId();

        } else if (followToBase.getNodeEnum().equals(NodeEnum.BUILD) || followToBase.getNodeEnum().equals(NodeEnum.DRAW)) {
            // pipe case
            String idPortion;

            if (typeSystem.isPredefinedSource(followToBase.getId())) {
                // Source case
                throw new ShouldNotHappenException(outPartOfChain, "A chain entering a Source??");
            } else if (typeSystem.isPredefinedOperation(followToBase.getId())) {
                // Operation case
                List<String> inChannelIds = typeSystem.getOperationInChannelIds(followToBase);
                idPortion = inChannelIds.get(0);
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

            AbstractNode parameterVariable = typeSystem.followNodeToBuildOfChannel(outPartOfChain, currentBlockId, currentSubScope);

            // The case where followToBase is a myChannel but followNodeToBuildOfChannel returns a Selector, is where we have a parameter we have to follow back to get the build statement of origin.
            if (((NamedNode) parameterVariable).getNodeEnum().equals(NodeEnum.SELECTOR) && parameterVariable.getChild() == null) {
                return procStack.peek().getBuildOfParameter((NamedIdNode) parameterVariable) + followToBase.getId();

            } else {
                // otherwise, it is one of the local block's myChannels
                return currentBlockId + followToBuild.getId();
            }

        } else {
            // channel variable case
            return followToBuild.toString()
                    + typeSystem.getSymbolTable().getBlockScope(followToBase.getId())
                    .getChannelDeclarationScope()
                    .getNode().findFirstChildOfClass(MyInChannelNode.class).getId();
        }
    }

    private void addAllChannelsOfBlockInstance(BuildNode instance, BlockScope theBlock) {
        // Get all channels
        Scope channelDeclarationScope = theBlock.getChannelDeclarationScope();

        // For all channel nodes add the channel id to the list
        for (NamedIdNode node = (NamedIdNode) channelDeclarationScope.getNode().getChild(); node != null; node = (NamedIdNode) node.getSib()) {
            switch (node.getNodeEnum()) {
                case CHANNEL_IN_MY:
                    outConnectPoints.add(instance + node.getId());
                    break;
                case CHANNEL_OUT_MY:
                    inConnectPoints.add(instance + node.getId());
                    break;
                default:
                    throw new ShouldNotHappenException(instance, "A non-mychannel in the channel scope?? " + instance.getId() + " - " + node);
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
            switch (node.getNodeEnum()) {
                case CHANNEL_IN_MY:
                    inConnectPoints.add(currentBlockId + node.getId());
                    break;
                case CHANNEL_OUT_MY:
                    outConnectPoints.add(currentBlockId + node.getId());
                    break;
                default:
                    throw new ShouldNotHappenException(node, "A non-mychannel in the channel scope?? " + currentBlockId + node);
            }
        }
    }

    private AbstractNode follow_SelectorDotSelectorSafe(AbstractNode node, String currentSubScope) {
        AbstractNode affasffs = typeSystem.followNodeToBuild(node, currentBlockId, currentSubScope);

        if (affasffs instanceof SelectorNode) {
            SelectorNode dummy = new SelectorNode(((SelectorNode) affasffs).getId(), new ComplexSymbolFactory.Location(node.getLineNumber(), node.getColumn()));
            dummy.setNumber(((SelectorNode) affasffs).getNumber());

            affasffs = typeSystem.followNodeToBuild(dummy, currentBlockId, currentSubScope);
        }

        return affasffs;
    }

    @SuppressWarnings("Duplicates")
    private void linkFromGroupMember(AbstractNode in, String out, String currentSubScope) {
        NamedIdNode followedToBuild = (NamedIdNode) typeSystem.followNodeToBuild(in, currentBlockId, currentSubScope);
        NamedIdNode followToBase = (NamedIdNode) typeSystem.followNode(in, currentBlockId, currentSubScope);
        boolean isSource = typeSystem.isPredefinedSource(followToBase.getId());
        boolean isOperation = typeSystem.isPredefinedOperation(followToBase.getId());
        String inString;

        if (followedToBuild.getNodeEnum().equals(NodeEnum.SELECTOR)) {
            if (followToBase.getId().equals("this")) {
                // this. case
                inString = currentBlockId + ((NamedIdNode) followToBase.getChild()).getId();

            } else {
                // var. case
                SelectorNode dummy = new SelectorNode(followToBase.getId(), new ComplexSymbolFactory.Location(in.getLineNumber(), in.getColumn()));
                dummy.setNumber(followToBase.getNumber());

                inString = typeSystem.followNodeToBuild(dummy, currentBlockId, currentSubScope) + ((NamedIdNode) followToBase.getChild()).getId();

            }

        } else if (isSource || isOperation) {
            // Source, Operation case
            List<String> outChannelIds = typeSystem.getOperationOrSourceOutChannelIds(followToBase);
            inString = followToBase + outChannelIds.get(0);

        } else if (followToBase.getNodeEnum().equals(NodeEnum.CHANNEL_OUT_MY) || followedToBuild.getNodeEnum().equals(NodeEnum.CHANNEL_IN_MY)) {
            // Own channel case
            AbstractNode parameterVariable = typeSystem.followNodeToBuildOfChannel(in, currentBlockId, currentSubScope);

            // The case where followToBase is a myChannel but followNodeToBuildOfChannel returns a Selector, is where we have a parameter we have to follow back to get the build statement of origin.
            if (((NamedNode) parameterVariable).getNodeEnum().equals(NodeEnum.SELECTOR) && parameterVariable.getChild() == null) {
                inString = procStack.peek().getBuildOfParameter((NamedIdNode) parameterVariable) + followToBase.getId();

            } else {
                // otherwise, it is one of the local block's myChannels
                inString = currentBlockId + followedToBuild.getId();
            }

        } else {
            // Block case
            inString = followedToBuild + typeSystem.getSymbolTable()
                    .getBlockScope(followToBase.getId())
                    .getChannelDeclarationScope().getNode()
                    .findFirstChildOfClass(MyOutChannelNode.class).getId();
        }

        addConnections(inString, out);
    }

    private Iterator<String> getInChannelIdsOfNode(AbstractNode node, String currentSubScope) {
        NamedIdNode followedToBuild = ((NamedIdNode) typeSystem.followNodeToBuild(node, currentBlockId, currentSubScope));
        NamedIdNode followedToBase = ((NamedIdNode) typeSystem.followNode(followedToBuild, currentBlockId, currentSubScope));
        boolean isSource = typeSystem.isPredefinedSource(followedToBase.getId());
        boolean isOperation = typeSystem.isPredefinedOperation(followedToBase.getId());

        List<String> inChannels = new ArrayList<>();

        if (isSource) {
            throw new ShouldNotHappenException(node, "Chain into source??");
        } else if (isOperation) {
            List<String> inChannelIds = typeSystem.getOperationInChannelIds(followedToBase);
            for (String id : inChannelIds) {
                inChannels.add(followedToBuild + id);
            }

        } else if (followedToBase.getNodeEnum().equals(NodeEnum.SELECTOR)) {
            // .notation case
            if (followedToBase.getId().equals("this")) {
                // this. case
                inChannels.add(currentBlockId + ((NamedIdNode) followedToBase.getChild()).getId());

            } else {
                // var. case
                inChannels.add(followedToBuild + ((NamedIdNode) followedToBase.getChild()).getId());

            }

        } else {
            for (AbstractNode inChannel : typeSystem.getSymbolTable()
                    .getBlockScope(followedToBase.getId())
                    .getChannelDeclarationScope().getAllChildrenOfType(NodeEnum.CHANNEL_IN_MY)) {
                inChannels.add(followedToBuild + ((NamedIdNode) inChannel).getId());
            }
        }

        return inChannels.iterator();
    }

    public String getCurrentBlockId() {
        return currentBlockId;
    }

    private void addConnections(String in, String out) {
        inConnections.add(in);
        outConnections.add(out);
    }

    private BlockNode blockNodeOfMyChannel(AbstractNode channel) {
        return (BlockNode) channel.getParent().getParent();
    }

    public void pushProcCall(ProcCall procCall) {
        procStack.push(procCall);
    }

    public void popProcCall() {
        procStack.pop();
    }
}
