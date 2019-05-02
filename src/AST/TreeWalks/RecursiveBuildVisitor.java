package AST.TreeWalks;

import AST.Nodes.AbstractNodes.Nodes.AbstractNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.BlockNode;
import AST.Nodes.NodeClasses.NamedNodes.ParamsNode;
import AST.TreeWalks.Exceptions.UnexpectedNodeException;
import AST.Visitor;
import SemanticAnalysis.Datastructures.SetStack;
import SemanticAnalysis.SemanticProblemException;
import SymbolTableImplementation.*;
import TypeChecker.TypeSystem;
import java_cup.runtime.Symbol;

public class RecursiveBuildVisitor implements Visitor {

    private SetStack<String> buildStack;
    private TypeSystem typeSystem;
    private String currentBlockScope;
    private String currentSubScope;

    public RecursiveBuildVisitor(SetStack<String> buildStack, SymbolTableInterface symbolTable) {
        this.buildStack = buildStack;
        this.typeSystem = new TypeSystem(symbolTable);
    }

    private RecursiveBuildVisitor(SetStack<String> buildStack, TypeSystem typeSystem) {
        this.buildStack = buildStack;
        this.typeSystem = typeSystem;
    }

    /**
     * @param printLevel   the level, used to decide how many indents there should be in the print statement.
     * @param node The node which is being visited.
     */
    @Override
    public void pre(int printLevel, AbstractNode node) {
        NamedNode namedNode = (NamedNode) node;
        NamedIdNode namedIdNode = (node instanceof NamedIdNode) ? (NamedIdNode) node : null;

        switch (namedNode.getNodeEnum()) {
            // Location enums
            case BLOCK:
                this.currentBlockScope = namedIdNode.getId();
                break;
            case BLUEPRINT:
                this.currentSubScope = BlockScope.BLUEPRINT;
                break;
            case PROCEDURE:
                this.currentSubScope = BlockScope.PROCEDURE_PREFIX + namedIdNode.getId();
                break;
            case CHANNEL_DECLARATIONS:
                this.currentSubScope = BlockScope.CHANNELS;
                break;

            // No action enums
            case ROOT:
            case GROUP:
            case CHAIN:
            case PROCEDURE_CALL:
            case PARAMS:
            case SELECTOR:
            case DRAW:
            case SIZE:
            case ASSIGN:
            case CHANNEL_IN_MY:
            case CHANNEL_OUT_MY:
            case CHANNEL_IN_TYPE:
            case CHANNEL_OUT_TYPE:
            case SIZE_TYPE:
            case BLOCK_TYPE:
            case SOURCE_TYPE:
            case BLUEPRINT_TYPE:
            case OPERATION_TYPE:
                break;

            case BUILD:
                this.handleBuild(node);

                break;

            default:
                throw new UnexpectedNodeException(namedNode);
        }
    }

    /**
     * @param printLevel   the level, used to decide how many indents there should be in the print statement.
     * @param abstractNode The node which is being visited.
     */
    @Override
    public void post(int printLevel, AbstractNode abstractNode) {
        NamedNode node = (NamedNode) abstractNode;

        switch (node.getNodeEnum()) {
            // No action enums
            case ROOT:
            case GROUP:
            case CHAIN:
            case PROCEDURE_CALL:
            case PARAMS:
            case SELECTOR:
            case DRAW:
            case SIZE:
            case ASSIGN:
            case BLOCK:
            case BLUEPRINT:
            case PROCEDURE:
            case CHANNEL_DECLARATIONS:
            case CHANNEL_IN_MY:
            case CHANNEL_OUT_MY:
            case CHANNEL_IN_TYPE:
            case CHANNEL_OUT_TYPE:
            case SIZE_TYPE:
            case BLOCK_TYPE:
            case SOURCE_TYPE:
            case BLUEPRINT_TYPE:
            case OPERATION_TYPE:
                break;

            case BUILD:
                // Once done, pop the top of the build stack.
                break;

            default:
                throw new UnexpectedNodeException(node);
        }
    }

    private void handleBuild(AbstractNode node) {
        String nodeSubType = this.typeSystem.getSubTypeOfNode(node, this.currentBlockScope, this.currentSubScope);

        boolean isSource = this.typeSystem.getSymbolTable().isPredefinedSource(nodeSubType);
        boolean isOperation = this.typeSystem.getSymbolTable().isPredefinedOperation(nodeSubType);

        if (isSource || isOperation) {
            // Do nothing
            return;
        }

        // Add the build to the build stack.
        boolean failure = !this.buildStack.push(this.convertBuildNodeToString(node));

        if (failure) {
            throw new SemanticProblemException("Recursive block building! - " + node);
        }
        
        // Find node we are building
        AbstractNode nodeBeingBuild = this.findNodeBeingBuild(node);

        // Jump to the build node, and walk it instead.
        nodeBeingBuild.walkTree(new RecursiveBuildVisitor(this.buildStack, this.typeSystem));

        // Once done, pop the newly added build
        this.buildStack.pop();
    }

    private AbstractNode findNodeBeingBuild(AbstractNode buildNode) {
        String buildSubType = this.typeSystem.getSubTypeOfNode(buildNode, this.currentBlockScope, this.currentSubScope);
        return this.typeSystem.getBlock(buildSubType);
    }

    public String convertBuildNodeToString(AbstractNode buildNode) {
        String buildTypeSubType = this.typeSystem.getSubTypeOfNode(buildNode, this.currentBlockScope, this.currentSubScope);
        String paramAsString = this.getParamAsString(buildNode);

        return buildTypeSubType + ": " + paramAsString;
    }

    private String getParamAsString(AbstractNode buildNode) {
        ParamsNode paramsNode = buildNode.findFirstChildOfClass(ParamsNode.class);

        if (paramsNode == null) {
            return "NoParams";

        } else {
            StringBuilder stringBuilder = new StringBuilder();

            for (AbstractNode child = paramsNode.getChild(); child != null; child = child.getSib()) {
                stringBuilder.append(this.convertSingleParamToString(child));
                stringBuilder.append(", ");
            }

            int delEnd = stringBuilder.length();
            int delStart = delEnd - 2;
            stringBuilder.delete(delStart, delEnd);

            return stringBuilder.toString();
        }
    }

    private String convertSingleParamToString(AbstractNode param) {
        return this.typeSystem.getSubTypeOfNode(param, this.currentBlockScope, this.currentSubScope);
    }

}
