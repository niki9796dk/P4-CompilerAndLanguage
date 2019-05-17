package AST.TreeWalks;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;
import AST.Nodes.NodeClasses.NamedNodes.ChainNode;
import AST.Nodes.NodeClasses.NamedNodes.GroupNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.BlockNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.BuildNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.SelectorNode;
import AST.Nodes.NodeClasses.NamedNodes.ParamsNode;
import AST.Nodes.NodeClasses.NamedNodes.SizeNode;
import AST.TreeWalks.Exceptions.UnexpectedNodeException;
import CodeGeneration.Building.BlockClass;
import CodeGeneration.Building.CodeScope;
import CodeGeneration.Building.CodeScopes.SimpleCodeScope;
import CodeGeneration.Building.Parameter;
import CodeGeneration.Building.Statement;
import CodeGeneration.Building.Statements.Assignments.AssignBuild;
import CodeGeneration.Building.Statements.Assignments.AssignVar;
import CodeGeneration.Building.Statements.Calls.CallParams;
import CodeGeneration.Building.Statements.Calls.ProcedureCall;
import CodeGeneration.Building.Statements.Connections.SingleChain;
import CodeGeneration.Building.Statements.Instantiations.InitBuild;
import CodeGeneration.Building.Statements.Instantiations.InitSize;
import CodeGeneration.Building.Statements.MyChannelDeclarations.BlockChannelDeclarationIn;
import CodeGeneration.Building.Statements.MyChannelDeclarations.BlockChannelDeclarationOut;
import CodeGeneration.Building.Statements.Selectors.DotSelector;
import CodeGeneration.Building.Statements.Selectors.Selector;
import CodeGeneration.Building.Statements.VariableDeclarations.*;
import DataStructures.Pair;
import SemanticAnalysis.Datastructures.HashSetStack;
import SemanticAnalysis.Exceptions.NoMainBlockException;
import SemanticAnalysis.Exceptions.SemanticProblemException;
import SymbolTableImplementation.BlockScope;
import SymbolTableImplementation.SymbolTable;

import java.util.*;

public class CodeGenerationVisitor extends ScopeTracker {
    private List<BlockClass> blockClasses = new ArrayList<>();
    private CodeScope currentCodeScope;

    public CodeGenerationVisitor(SymbolTable symbolTable) {
        super(symbolTable);
    }

    /**
     * @param printLevel   the level, used to decide how many indents there should be in the print statement.
     * @param abstractNode The node which is being visited.
     */
    @Override
    @SuppressWarnings("Duplicates")
    public void pre(int printLevel, AbstractNode abstractNode) {
        super.pre(printLevel, abstractNode);

        NamedNode node = (NamedNode) abstractNode;

        String nodeId = (node instanceof NamedIdNode) ? ((NamedIdNode) node).getId() : null;

        switch (node.getNodeEnum()) {
            // Setup enums
            case BLOCK:
                this.blockClasses.add(new BlockClass(nodeId, "CodeGeneration.Building"));
                break;

            case CHANNEL_DECLARATIONS:
            case BLUEPRINT:
                this.currentCodeScope = this.getLastBlock().getBlueprint();
                break;

            case PROCEDURE:
                this.currentCodeScope = new SimpleCodeScope(BlockScope.PROCEDURE_PREFIX + nodeId);
                this.getLastBlock().addProcedure(this.currentCodeScope);
                break;

            // Variable declaration enums
            case CHANNEL_IN_MY:
            case CHANNEL_OUT_MY:
            case CHANNEL_IN_TYPE:
            case CHANNEL_OUT_TYPE:
            case OPERATION_TYPE:
            case BLOCK_TYPE:
            case SOURCE_TYPE:
            case SIZE_TYPE:
                if (node.getParent() instanceof ParamsNode) {
                    this.currentCodeScope.addParameter((Parameter) this.getStatementFromNode(node));
                } else {
                    this.currentCodeScope.addStatement(this.getStatementFromNode(node));
                }

                break;

            case PROCEDURE_CALL:
                this.currentCodeScope.addStatement(this.getStatementFromNode(node));
                break;

            case CHAIN:
                this.handleChainNode((ChainNode) node);
                break;

            // No action enums
            case SELECTOR:      // Handled by other enums
            case DRAW:          // Handled by other enums
            case SIZE:          // Handled by other enums
            case BUILD:         // Handled by other enums
            case PARAMS:        // Handled in calls / builds
            case GROUP:         // Handled in chain
            case ASSIGN:        // Post Order
            case ROOT:
                break;

            default:
                throw new UnexpectedNodeException(node);
        }
    }

    /**
     * @param printLevel   the level, used to decide how many indents there should be in the print statement.
     * @param abstractNode The node which is being visited.
     */
    @SuppressWarnings("Duplicates")
    @Override
    public void post(int printLevel, AbstractNode abstractNode) {
        super.post(printLevel, abstractNode);

        NamedNode node = (NamedNode) abstractNode;

        switch (node.getNodeEnum()) {
            // No action enums
            case ROOT:
                System.out.println(this.blockClasses.get(0).toString());
            break;

            case GROUP:
            case CHAIN:
            case PROCEDURE_CALL:
            case PARAMS:
            case SELECTOR:
            case DRAW:
            case BUILD:
            case SIZE:
            case BLOCK:
            case BLUEPRINT:
            case PROCEDURE:
            case CHANNEL_IN_MY:
            case CHANNEL_OUT_MY:
            case CHANNEL_IN_TYPE:
            case CHANNEL_OUT_TYPE:
            case SIZE_TYPE:
            case BLOCK_TYPE:
            case SOURCE_TYPE:
            case BLUEPRINT_TYPE:
            case OPERATION_TYPE:
            case CHANNEL_DECLARATIONS:
                break;

            case ASSIGN:
                NamedIdNode leftNode = (NamedIdNode) node.getChild();
                AbstractNode rightNode = leftNode.getSib();

                String leftVar = leftNode.getId();

                if (rightNode instanceof BuildNode) {
                    this.currentCodeScope.addStatement(new AssignBuild(leftVar, (InitBuild) this.getStatementFromNode(rightNode)));
                } else if (rightNode instanceof SelectorNode) {
                    this.currentCodeScope.addStatement(new AssignVar(leftVar, this.getStatementFromNode(rightNode).toString()));
                } else {
                    throw new IllegalArgumentException("Did not expect this: " + rightNode);
                }
                break;

            default:
                throw new UnexpectedNodeException(node);
        }
    }

    private void handleChainNode(ChainNode chainNode) {
        for (AbstractNode leftNode = chainNode.getChild(); leftNode.getSib() != null; leftNode = leftNode.getSib()) {
            AbstractNode rightNode = leftNode.getSib();

            this.connect(leftNode, rightNode);
        }
    }

    private void connect(AbstractNode leftNode, AbstractNode rightNode) {
        if (leftNode instanceof GroupNode) {
            this.groupConnect(leftNode, rightNode);
        } else {
            this.singleConnect(leftNode, rightNode);
        }
    }

    private void groupConnect(AbstractNode groupNode, AbstractNode rightNode) {
        int groupElems = groupNode.countChildren();

        for (int i = 0; i < groupElems; i++) {

        }
    }

    private void singleConnect(AbstractNode leftNode, AbstractNode rightNode) {
        Pair<String, String> leftInfo = this.transformElemForChaining(leftNode);
        Pair<String, String> rightInfo = this.transformElemForChaining(rightNode);

        SingleChain chainStatement = new SingleChain(
                leftInfo.getKey(), leftInfo.getValue(),
                rightInfo.getKey(), rightInfo.getValue()
        );

        this.currentCodeScope.addStatement(chainStatement);
    }

    // Pair<Element, channelId>
    private Pair<String, String> transformElemForChaining(AbstractNode node) {
        return null;
    }

    private Statement getStatementFromNode(AbstractNode abstractNode) {
        NamedNode node = (NamedNode) abstractNode;

        String nodeId = (node instanceof NamedIdNode) ? ((NamedIdNode) node).getId() : null;

        switch (node.getNodeEnum()) {
            // Setup enums
            case BLOCK:
            case BLUEPRINT:
            case PROCEDURE:
            case CHANNEL_DECLARATIONS:
                break;

            // Variable declaration enums
            case OPERATION_TYPE:
                return new OperationDeclaration(nodeId);
            case BLOCK_TYPE:
                return new BlockDeclaration(nodeId);
            case SOURCE_TYPE:
                return new SourceDeclaration(nodeId);
            case SIZE_TYPE:
                return new SizeDeclaration(nodeId);

            // Init enums
            case SIZE:
                SizeNode sizeNode = (SizeNode) node;
                return new InitSize(sizeNode.first, sizeNode.second);

            case BUILD:
                NodeEnum superType = this.typeSystem.getSuperTypeOfNode(node, this.currentBlockScope, this.currentSubScope);

                switch (superType) {
                    case BLOCK_TYPE:
                    case SOURCE_TYPE:
                    case OPERATION_TYPE:
                        String subTypeOfBuild = this.typeSystem.getSubTypeOfNode(node, this.currentBlockScope, this.currentSubScope);

                        ParamsNode params = node.findFirstChildOfClass(ParamsNode.class);

                        if (params != null) {
                            return new InitBuild(subTypeOfBuild, this.getStatementFromNode(params));

                        } else {
                            return new InitBuild(subTypeOfBuild);
                        }

                    case BLUEPRINT_TYPE:
                        return null; // TODO: FIX

                }

            case SELECTOR:
                if (node.getChild() instanceof SelectorNode) {
                    return new DotSelector(nodeId, ((NamedIdNode) node.getChild()).getId());
                } else {
                    return new Selector(nodeId);
                }

            case PROCEDURE_CALL:
                ParamsNode params = node.findFirstChildOfClass(ParamsNode.class);
                SelectorNode procSelector = node.findFirstChildOfClass(SelectorNode.class);

                if (params != null) {
                    return new ProcedureCall(procSelector.getId(), (CallParams) this.getStatementFromNode(params));

                } else {
                    return new ProcedureCall(procSelector.getId());
                }

            case PARAMS:
                List<Statement> buildParams = new ArrayList<>();

                for (AbstractNode pNode = node.getChild(); pNode != null; pNode = pNode.getSib()) {
                    buildParams.add(this.getStatementFromNode(pNode));
                }

                return new CallParams(buildParams.toArray(new Statement[0]));

            case CHANNEL_IN_MY:
                if (BlockScope.CHANNELS.equals(this.currentSubScope)) {
                    return new BlockChannelDeclarationIn(nodeId);
                } else {
                    return new ChannelDeclaration(nodeId);
                }
            case CHANNEL_OUT_MY:
                if (BlockScope.CHANNELS.equals(this.currentSubScope)) {
                    return new BlockChannelDeclarationOut(nodeId);
                } else {
                    return new ChannelDeclaration(nodeId);
                }

            case CHANNEL_IN_TYPE:
            case CHANNEL_OUT_TYPE:
                return new ChannelDeclaration(nodeId);

            case GROUP:
            case CHAIN:
            case DRAW:
            case ASSIGN:
                break;

            // No action enums
            case ROOT:

            default:
                throw new UnexpectedNodeException(node);
        }

        return null;
    }

    private BlockClass getLastBlock() {
        return this.blockClasses.get(this.blockClasses.size()-1);
    }
}
