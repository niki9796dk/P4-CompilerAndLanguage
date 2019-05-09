package AST.TreeWalks;

import AST.Nodes.AbstractNodes.Nodes.AbstractNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;
import AST.Nodes.NodeClasses.NamedNodes.BlueprintNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.BlockNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.ProcedureNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.SelectorNode;
import AST.Nodes.NodeClasses.NamedNodes.ParamsNode;
import AST.Nodes.NodeClasses.NamedNodes.ProcedureCallNode;
import AST.TreeWalks.Exceptions.UnexpectedNodeException;
import AST.Visitor;
import SymbolTableImplementation.*;
import TypeChecker.Exceptions.ShouldNotHappenException;
import TypeChecker.TypeSystem;

/**
 * Abstract Visitor class - ScopeTracker.
 * A Visitor class which can be extended to avoid tracking current block and sub scopes in every Visitor implementation
 * When extending this class, in both pre() and post() should super.pre() / super.post() be called, to update the tracker.
 */
public class RecursiveVisitor extends ScopeTracker {

    private static int counter = 0;

    private ScopeTracker internalVisitor;

    public RecursiveVisitor(SymbolTable symbolTable, ScopeTracker internalVisitor) {
        super(symbolTable);
        this.internalVisitor = internalVisitor;
        counter = counter++;
    }

    protected RecursiveVisitor(SymbolTable symbolTable, ScopeTracker internalVisitor, String currentBlockScope, String currentSubScope) {
        super(symbolTable, currentBlockScope, currentSubScope);
        this.internalVisitor = internalVisitor;
        counter = counter++;
    }

    public void startRecursiveWalk() {
        System.out.println("#############################\n");
        for (BlockNode mainBlock : this.symbolTable.getMainBlocks()) {
            System.out.println("Walking: " + mainBlock.toString().stripLeading());

            this.setCurrentBlockScope(mainBlock.getId());
            this.internalVisitor.setCurrentBlockScope(mainBlock.getId());
            mainBlock.findFirstChildOfClass(BlueprintNode.class).walkTree(this);
        }
    }

    /**
     * @param printLevel    the level, used to decide how many indents there should be in the print statement.
     * @param node          The node which is being visited.
     */
    @Override
    public void pre(int printLevel, AbstractNode node) {
        for (int i = 0; i < printLevel; i++) {
            System.out.print("\t");
        }

        System.out.println(node);

        super.pre(printLevel, node);
        this.internalVisitor.pre(printLevel, node);
    }

    /**
     * @param printLevel   the level, used to decide how many indents there should be in the print statement.
     * @param abstractNode The node which is being visited.
     */
    @Override
    public void post(int printLevel, AbstractNode abstractNode) {
        super.post(printLevel, abstractNode);
        this.internalVisitor.post(printLevel, abstractNode);



        NamedNode node = (NamedNode) abstractNode;

        switch (node.getNodeEnum()) {
            // No action enums
            case ROOT:
            case GROUP:
            case CHAIN:
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

            case PROCEDURE_CALL:
                handleRecursiveProcedureCall((ProcedureCallNode) node);
                break;

            case BUILD:
                break;

            default:
                throw new UnexpectedNodeException(node);
        }
    }

    private void handleRecursiveProcedureCall(ProcedureCallNode callNode) {
        System.out.print("\tHandle[" + counter + "]: " + callNode + " - " + callNode.findFirstChildOfClass(SelectorNode.class));

        // Find procedure call ID
        String procedureId = BlockScope.PROCEDURE_PREFIX + callNode.findFirstChildOfClass(SelectorNode.class).getId();

        // Verify that it exist.
        Scope procedureSubScope = this.symbolTable.getSubScope(this.currentBlockScope, procedureId);
        boolean fakeProcedureCall = procedureSubScope == null;

        if (fakeProcedureCall) {
            throw new ShouldNotHappenException("No such procedure???: " + callNode);
        }

        // Verify that if params is needed, that they are there!
        ParamsNode callerParams = callNode.findFirstChildOfClass(ParamsNode.class);
        ParamsNode calleeParams = procedureSubScope.getNode().findFirstChildOfClass(ParamsNode.class);

        boolean hasParams = callerParams != null;
        boolean needsParams = calleeParams != null;

        if (hasParams != needsParams) {
            throw new ShouldNotHappenException("Inconsistency in the given and expected parameter count: " + callNode);
        }

        // Verify the parameter count
        if (needsParams) {
            int callerParamsCount = callerParams.countChildren();
            int calleeParamsCount = calleeParams.countChildren();

            if (callerParamsCount != calleeParamsCount) {
                throw new ShouldNotHappenException("Inconsistency in the given and expected parameter count: " + callNode);
            }

            // Loop all params and link them.
            AbstractNode actualParam = callerParams.getChild();
            AbstractNode formalParam = calleeParams.getChild();
            for (int i = 0; i < callerParamsCount; i++) {
                // Link params
                ((VariableEntryOrDefault) procedureSubScope.getVariable(formalParam)).setDefaultSubtype(this.convertParamForLinking((NamedNode) actualParam));

                // Update params "counter"
                actualParam = actualParam.getSib();
                formalParam = formalParam.getSib();
            }
        }

        // Recursively call the procedure with the new param bindings.
        System.out.println(" - Jump to: " + procedureSubScope.getNode() + " - From: " + this.currentBlockScope + ", " + this.currentSubScope);

        String oldSubScope = this.currentSubScope;
        this.internalVisitor.setCurrentSubScope(procedureId); // Update sub scope
        procedureSubScope.getNode().walkTree(new RecursiveVisitor(this.symbolTable, this.internalVisitor, this.currentBlockScope, procedureId));
        this.internalVisitor.setCurrentSubScope(oldSubScope); // Revert sub scope
    }

    private NamedNode convertParamForLinking(NamedNode node) {
        switch (node.getNodeEnum()) {
            // Location enums
            case BLOCK:
            case BLUEPRINT:
            case PROCEDURE:
            case CHANNEL_DECLARATIONS:
            case ROOT:
            case GROUP:
            case CHAIN:
            case PROCEDURE_CALL:
            case PARAMS:
            case SIZE_TYPE:
            case BLOCK_TYPE:
            case SOURCE_TYPE:
            case BLUEPRINT_TYPE:
            case OPERATION_TYPE:
                throw new RuntimeException("Should not be possible as a parameter?!?");

            case ASSIGN:
                return this.convertParamForLinking((NamedNode) node.getChild().getSib()); // Convert right side of the assignment, since this is the value of the expression.

            case DRAW:
            case BUILD:
            case SIZE:
            case CHANNEL_IN_MY:
            case CHANNEL_OUT_MY:
            case CHANNEL_IN_TYPE:
            case CHANNEL_OUT_TYPE:
                return node;

            case SELECTOR:
                return this.handleSelectorConversion((SelectorNode) node);

            default:
                throw new UnexpectedNodeException(node);
        }
    }

    private NamedNode handleSelectorConversion(SelectorNode node) {
        boolean hasSelectorChild = node.getChild() instanceof SelectorNode;
        boolean isThis = "this".equals(node.getId());

        if (isThis) {
            return this.symbolTable
                    .getBlockScope(this.currentBlockScope)
                    .getChannelDeclarationScope()
                    .getVariable(((SelectorNode) node.getChild()).getId())
                    .getNode();

        } else if (hasSelectorChild) {
            // Problematic since we need to link to another blocks channels, without returning the wrong channel type.
            SelectorNode newSelector = new SelectorNode(node.getId());
            newSelector.setNumber(node.getNumber());

            String elementId = this.typeSystem.getSubTypeOfNode(newSelector, this.currentBlockScope, this.currentSubScope);
            String childId = ((NamedIdNode) node.getChild()).getId();

            return this.symbolTable.getBlockScope(elementId).getChannelDeclarationScope().getVariable(childId).getNode();

        } else {
            VariableEntry selectorVariable = this.symbolTable.getSubScope(this.currentBlockScope, this.currentSubScope).getVariable(node.getId());
            NamedIdNode subtypeNode = selectorVariable.getSubType(node.getNumber());

            if (subtypeNode instanceof SelectorNode) {
                return this.handleSelectorConversion((SelectorNode) subtypeNode);
            } else {
                return this.convertParamForLinking(subtypeNode);
            }
        }
    }
}
