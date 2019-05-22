package AST.TreeWalks;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Node;
import AST.Nodes.AbstractNodes.Nodes.AbstractNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;
import AST.Nodes.NodeClasses.NamedNodes.*;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.BuildNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.DrawNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.SelectorNode;
import AST.TreeWalks.Exceptions.UnexpectedNodeException;
import CodeGeneration.Building.BlockClass;
import CodeGeneration.Building.CodeScope;
import CodeGeneration.Building.CodeScopes.SimpleCodeScope;
import CodeGeneration.Building.Parameter;
import CodeGeneration.Building.Statement;
import CodeGeneration.Building.Statements.Assignments.AssignBlueprint;
import CodeGeneration.Building.Statements.Assignments.AssignBuild;
import CodeGeneration.Building.Statements.Assignments.AssignSize;
import CodeGeneration.Building.Statements.Assignments.AssignVar;
import CodeGeneration.Building.Statements.Calls.CallParams;
import CodeGeneration.Building.Statements.Calls.ProcedureCall;
import CodeGeneration.Building.Statements.Connections.GroupChain;
import CodeGeneration.Building.Statements.Connections.NodeToChannelChain;
import CodeGeneration.Building.Statements.Connections.TetherChannels;
import CodeGeneration.Building.Statements.Instantiations.InitBlueprint;
import CodeGeneration.Building.Statements.Instantiations.InitBuild;
import CodeGeneration.Building.Statements.Instantiations.InitBuildBlueprint;
import CodeGeneration.Building.Statements.Instantiations.InitSize;
import CodeGeneration.Building.Statements.MyChannelDeclarations.BlockChannelDeclarationIn;
import CodeGeneration.Building.Statements.MyChannelDeclarations.BlockChannelDeclarationOut;
import CodeGeneration.Building.Statements.Selectors.DotSelector;
import CodeGeneration.Building.Statements.Selectors.Selector;
import CodeGeneration.Building.Statements.VariableDeclarations.*;
import DataStructures.Pair;
import SymbolTableImplementation.BlockScope;
import SymbolTableImplementation.Scope;
import SymbolTableImplementation.SymbolTable;

import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class CodeGenerationVisitor extends ScopeTracker {
    private List<BlockClass> blockClasses = new ArrayList<>();
    private CodeScope currentCodeScope;
    private Map<Integer, String> placeholderVars = new HashMap<>();
    private String programFileName;
    private static String EXPORT_PATH = "src\\AutoGen\\CodeGen\\";
    private static String EXPORT_PACKAGE = "AutoGen.CodeGen";

    public CodeGenerationVisitor(SymbolTable symbolTable, String programFileName) {
        super(symbolTable);
        this.programFileName = programFileName;
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
                // Import the new block in all previous defined blocks
                for (BlockClass blockClass : this.blockClasses) {
                    blockClass.addImport(EXPORT_PACKAGE + "." + programFileName + "." + nodeId);
                }

                // Add the new block to the list of block classes
                this.blockClasses.add(new BlockClass(nodeId, EXPORT_PACKAGE + "." + programFileName));
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
            case BLUEPRINT_TYPE:
                Statement statement = this.getStatementFromNode(node);

                if (node.getParent() instanceof ParamsNode) {
                    this.currentCodeScope.addParameter((Parameter) statement);
                } else {
                    this.currentCodeScope.addStatement(statement);
                }
                break;


            case BUILD:         // Also handled by other enums
                if (node.getParent() instanceof ChainNode) {
                    // Generate unique var name
                    String uniqueVarName = this.generateUniqueVariableName(node);
                    // Add statements to reflect the new variables declaration
                    this.currentCodeScope.addStatement(new BlockDeclaration(uniqueVarName));
                    this.currentCodeScope.addStatement(new AssignBuild(uniqueVarName, this.getStatementFromNode(node)));
                    // Insert the var name into a map for later retrieval (MUST BE PLACED AFTER THE ABOVE STATEMENTS)
                    this.placeholderVars.put(node.getNumber(), uniqueVarName);
                }
                break;

            // No action enums
            case PROCEDURE_CALL:// Post order
            case CHAIN:         // Post order
            case SELECTOR:      // Handled by other enums
            case DRAW:          // Handled by other enums
            case SIZE:          // Handled by other enums
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
                String exportPackagePath = EXPORT_PATH + programFileName + "/";

                try {
                    if (!Files.exists(Paths.get(EXPORT_PATH))) {
                        Files.createDirectories(Paths.get(EXPORT_PATH));
                    }

                    if (!Files.exists(Paths.get(exportPackagePath))) {
                        Files.createDirectories(Paths.get(exportPackagePath));
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                for (BlockClass blockClass : this.blockClasses) {
                    Path filePath = Paths.get(exportPackagePath + blockClass.getClassName() + ".java");

                    try (Writer writer = Files.newBufferedWriter(filePath)) {

                        writer.write(blockClass.toString());

                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            break;

            case GROUP:
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

            case PROCEDURE_CALL:
                this.currentCodeScope.addStatement(this.getStatementFromNode(node));
                break;

            case CHAIN:
                this.handleChainNode((ChainNode) node);
                break;

            case ASSIGN:
                NamedIdNode leftNode = (NamedIdNode) node.getChild();
                AbstractNode rightNode = leftNode.getSib();

                String leftVar = leftNode.getId();

                if (rightNode instanceof BuildNode) {
                    Statement buildStatement = this.getStatementFromNode(rightNode);
                    Scope subScope = this.symbolTable.getSubScope(this.currentBlockScope, this.currentSubScope);

                    this.currentCodeScope.addStatement(new AssignBuild(subScope, leftVar, buildStatement));
                } else if (rightNode instanceof SelectorNode) {
                    this.currentCodeScope.addStatement(new AssignVar(leftVar, this.getStatementFromNode(rightNode).toString()));
                } else if (rightNode instanceof DrawNode) {
                    this.currentCodeScope.addStatement(new AssignBlueprint(leftVar, (InitBlueprint) this.getStatementFromNode(rightNode)));
                } else if (rightNode instanceof SizeNode) {
                    this.currentCodeScope.addStatement(new AssignSize(leftVar, (InitSize) this.getStatementFromNode(rightNode)));
                } else {
                    throw new IllegalArgumentException("Did not expect this: " + rightNode);
                }
                break;

            default:
                throw new UnexpectedNodeException(node);
        }
    }

    private String generateUniqueVariableName(Node node) {
        String nodeId = ((NamedIdNode) node).getId();
        Scope currentScope = this.symbolTable.getSubScope(this.currentBlockScope, this.currentSubScope);

        String uniqueVar;
        do {
            int randomNumber = Math.abs(ThreadLocalRandom.current().nextInt());

            uniqueVar = "_" + nodeId + "_" + randomNumber;
        } while (currentScope.getVariable(uniqueVar) != null);

        return uniqueVar;
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
        rightNode = this.transformIfAssign(rightNode);
        Statement rightStatement = this.getStatementFromNode(rightNode);

        int groupElems = groupNode.countChildren();

        List<Statement> groupElements = new ArrayList<>(groupElems);

        AbstractNode groupElement = groupNode.getChild();
        for (int i = 0; i < groupElems; i++) {
            groupElements.add(this.getStatementFromNode(groupElement));
            groupElement = groupElement.getSib();
        }

        this.currentCodeScope.addStatement(new GroupChain(this.getCurrentSubScope(), rightStatement, groupElements.toArray(new Statement[0])));
    }

    private void singleConnect(AbstractNode leftNode, AbstractNode rightNode) {
        leftNode = this.transformIfAssign(leftNode);
        rightNode = this.transformIfAssign(rightNode);

        Statement leftStatement = this.getStatementFromNode(leftNode);
        Statement rightStatement = this.getStatementFromNode(rightNode);

        if ((this.isBlockOperationSource(leftNode) || this.isChannel(leftNode)) && this.isBlockOperationSource(rightNode)) {
            this.currentCodeScope.addStatement(new GroupChain(this.getCurrentSubScope(), rightStatement, leftStatement));

        } else if (this.isChannel(leftNode) && this.isChannel(rightNode)) {
            this.currentCodeScope.addStatement(new TetherChannels(leftStatement, rightStatement));

        } else if (this.isBlockOperationSource(leftNode) && isChannel(rightNode)) {
            this.currentCodeScope.addStatement(new NodeToChannelChain(leftStatement, rightStatement));

        } else {
            throw new RuntimeException("Left: " + leftNode + " - Right: " + rightNode);
        }

    }

    private AbstractNode transformIfAssign(AbstractNode node) {
        if (node instanceof AssignNode) {
            SelectorNode selectorNode = new SelectorNode(((NamedIdNode) node.getChild()).getId());
            selectorNode.setNumber(((NamedNode) node).getNumber());

            return selectorNode;
        } else {
            return node;
        }
    }

    private boolean isBlockOperationSource(AbstractNode node) {

        if (node instanceof BuildNode) {
            return true;
        } else {
            NodeEnum superType = this.typeSystem.getSuperTypeOfNode(node, this.currentBlockScope, this.currentSubScope);

            switch (superType) {
                case BLOCK_TYPE:
                case OPERATION_TYPE:
                case SOURCE_TYPE:
                    return true;
            }
        }

        return false;
    }

    private boolean isChannel(AbstractNode node) {

        if (node instanceof BuildNode) {
            return false;
        } else {
            NodeEnum superType = this.typeSystem.getSuperTypeOfNode(node, this.currentBlockScope, this.currentSubScope);

            switch (superType) {
                case CHANNEL_OUT_TYPE:
                case CHANNEL_IN_TYPE:
                case CHANNEL_IN_MY:
                case CHANNEL_OUT_MY:
                    return true;
            }
        }

        return false;
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
            case BLUEPRINT_TYPE:
                return new BlueprintDeclaration(nodeId);

                // Init enums
            case SIZE:
                SizeNode sizeNode = (SizeNode) node;
                return new InitSize(sizeNode.first, sizeNode.second);

            case DRAW:
                return new InitBlueprint(nodeId);

            case BUILD:
                if (node.getParent() instanceof ChainNode && this.placeholderVars.containsKey(node.getNumber())) {
                    return new Selector(this.placeholderVars.get(node.getNumber()));
                }

                boolean isNotBlueprintBuild = this.symbolTable.getSubScope(this.currentBlockScope, this.currentSubScope).getVariable(nodeId) == null;

                if (isNotBlueprintBuild) {
                    String subTypeOfBuild = this.typeSystem.getSubTypeOfNode(node, this.currentBlockScope, this.currentSubScope);

                    ParamsNode buildParams = node.findFirstChildOfClass(ParamsNode.class);

                    if (buildParams != null) {
                        return new InitBuild(subTypeOfBuild, this.getStatementFromNode(buildParams));

                    } else {
                        return new InitBuild(subTypeOfBuild);
                    }
                } else {
                    ParamsNode buildParams = node.findFirstChildOfClass(ParamsNode.class);

                    if (buildParams != null) {
                        return new InitBuildBlueprint(nodeId, (CallParams) this.getStatementFromNode(buildParams));

                    } else {
                        return new InitBuildBlueprint(nodeId);
                    }
                }

            case SELECTOR:
                if (node.getChild() instanceof SelectorNode) {
                    return new DotSelector(nodeId, ((NamedIdNode) node.getChild()).getId());
                } else {
                    NodeEnum nodeSuperType = this.typeSystem.getSuperTypeOfNode(node, this.currentBlockScope, this.currentSubScope);

                    if (nodeSuperType == NodeEnum.CHANNEL_IN_MY || nodeSuperType == NodeEnum.CHANNEL_OUT_MY) {
                        return new DotSelector("this", nodeId);
                    } else {
                        return new Selector(nodeId);
                    }
                }

            case PROCEDURE_CALL:
                ParamsNode procedureParams = node.findFirstChildOfClass(ParamsNode.class);
                SelectorNode procSelector = node.findFirstChildOfClass(SelectorNode.class);

                if (procedureParams != null) {
                    return new ProcedureCall(this.getCurrentSubScope(), procSelector.getId(), (CallParams) this.getStatementFromNode(procedureParams));

                } else {
                    return new ProcedureCall(this.getCurrentSubScope(), procSelector.getId());
                }

            case PARAMS:
                List<Statement> params = new ArrayList<>();
                Scope currentScope = this.symbolTable.getSubScope(this.currentBlockScope, this.currentSubScope);

                for (AbstractNode pNode = node.getChild(); pNode != null; pNode = pNode.getSib()) {
                    params.add(this.getStatementFromNode(pNode));
                }

                return new CallParams(currentScope, params.toArray(new Statement[0]));

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
