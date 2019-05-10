package AST.TreeWalks;

import AST.Nodes.AbstractNodes.Nodes.AbstractNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;
import AST.Nodes.NodeClasses.NamedNodes.BlueprintNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.BlockNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.BuildNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.SelectorNode;
import AST.Nodes.NodeClasses.NamedNodes.ParamsNode;
import AST.Nodes.NodeClasses.NamedNodes.ProcedureCallNode;
import AST.TreeWalks.Exceptions.UnexpectedNodeException;
import SymbolTableImplementation.*;
import TypeChecker.Exceptions.ParamsSizeInconsistencyException;
import TypeChecker.Exceptions.ShouldNotHappenException;

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
     * @param printLevel    The level, used to decide how many indents there should be in the print statement.
     * @param abstractNode  The node which is being visited.
     */
    @Override
    public void pre(int printLevel, AbstractNode abstractNode) {
        for (int i = 0; i < printLevel; i++) {
            System.out.print("\t");
        }

        System.out.println(abstractNode);

        super.pre(printLevel, abstractNode);
        this.internalVisitor.pre(printLevel, abstractNode);

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
            case BUILD:
                handleRecursiveCall(node);
                break;

            default:
                throw new UnexpectedNodeException(node);
        }
    }

    /**
     * @param printLevel   the level, used to decide how many indents there should be in the print statement.
     * @param abstractNode The node which is being visited.
     */
    @Override
    public void post(int printLevel, AbstractNode abstractNode) {
        super.post(printLevel, abstractNode);
        this.internalVisitor.post(printLevel, abstractNode);
    }

    private void handleRecursiveCall(NamedNode callNode) {
        System.out.print("\tHandle[" + counter + "]: " + callNode + " - " + callNode.findFirstChildOfClass(SelectorNode.class));

        // Find procedure call ID
        String calleeId = this.getCalleeId(callNode);

        // If it's a build node, and it's a source or operation, simple ignore it.
        if (callNode instanceof BuildNode) {
            boolean isSource = this.symbolTable.isPredefinedSource(calleeId);
            boolean isOperation = this.symbolTable.isPredefinedOperation(calleeId);

            if (isSource || isOperation) {
                return;
                // Skip those builds
            }
        }

        // Get the procedure sub scope
        Scope calleeSubScope = this.getCalleeSubScope(callNode, calleeId);

        // Assert that it exist
        this.assertNotNull(calleeSubScope);

        // Verify that if params is needed, that they are there!
        ParamsNode callerParams = callNode.findFirstChildOfClass(ParamsNode.class);
        ParamsNode calleeParams = calleeSubScope.getNode().findFirstChildOfClass(ParamsNode.class);

        boolean hasParams = callerParams != null;
        boolean needsParams = calleeParams != null;

        if (hasParams != needsParams) {
            throw new ParamsSizeInconsistencyException("Inconsistency in the given and expected parameter count: " + callNode);
        }

        // Verify the parameter count
        if (needsParams) {
            int callerParamsCount = callerParams.countChildren();
            int calleeParamsCount = calleeParams.countChildren();

            if (callerParamsCount != calleeParamsCount) {
                throw new ParamsSizeInconsistencyException("Inconsistency in the given and expected parameter count: " + callNode);
            }

            // Loop all params and link them.
            AbstractNode actualParam = callerParams.getChild();
            AbstractNode formalParam = calleeParams.getChild();
            for (int i = 0; i < callerParamsCount; i++) {
                // Link params
                ((VariableEntryOrDefault) calleeSubScope.getVariable(formalParam)).setDefaultSubtype(this.convertParamForLinking((NamedNode) actualParam));

                // Update params "counter"
                actualParam = actualParam.getSib();
                formalParam = formalParam.getSib();
            }
        }

        // Recursively call the procedure with the new param bindings.
        System.out.println(" - Jump to: " + calleeSubScope.getNode() + " - From: " + this.currentBlockScope + ", " + this.currentSubScope);

        this.jumpToCallee(callNode, calleeSubScope, calleeId);
    }

    private void jumpToCallee(NamedNode node, Scope calleeSubScope, String calleeId) {
        System.out.println(this.symbolTable);

        switch (node.getNodeEnum()) {
            case PROCEDURE_CALL:
                this.jumpToProcedure(calleeSubScope, calleeId);
                break;

            case BUILD:
                this.jumpToBlock(calleeSubScope, calleeId);
                break;

            default:
                throw new UnexpectedNodeException(node);
        }
    }

    private void jumpToProcedure(Scope calleeSubScope, String calleeId) {
        String oldSubScope = this.currentSubScope;
        this.internalVisitor.setCurrentSubScope(calleeId); // Update sub scope
        calleeSubScope.getNode().walkTree(new RecursiveVisitor(this.symbolTable, this.internalVisitor, this.currentBlockScope, calleeId));
        this.internalVisitor.setCurrentSubScope(oldSubScope); // Revert sub scope
    }

    private void jumpToBlock(Scope calleeSubScope, String calleeId) {
        String oldBlockScope = this.currentBlockScope;
        String oldSubScope = this.currentSubScope;

        this.internalVisitor.setCurrentBlockScope(calleeId); // Update block scope
        this.internalVisitor.setCurrentSubScope(calleeId); // Update sub scope

        calleeSubScope.getNode().walkTree(new RecursiveVisitor(this.symbolTable, this.internalVisitor, calleeId, BlockScope.BLUEPRINT));

        this.internalVisitor.setCurrentBlockScope(oldBlockScope); // Revert block scope
        this.internalVisitor.setCurrentSubScope(oldSubScope); // Revert sub scope
    }

    private String getCalleeId(NamedNode node) {
        switch (node.getNodeEnum()) {
            case PROCEDURE_CALL:
                return this.getProcedureId((ProcedureCallNode) node);
            case BUILD:
                return this.getBuildBlockId((BuildNode) node);
            default:
                throw new UnexpectedNodeException(node);
        }
    }

    private String getProcedureId(ProcedureCallNode callNode) {
        return BlockScope.PROCEDURE_PREFIX + callNode.findFirstChildOfClass(SelectorNode.class).getId();
    }

    private String getBuildBlockId(BuildNode buildNode) {
        return this.typeSystem.getSubTypeOfNode(buildNode, this.currentBlockScope, this.currentSubScope);
    }

    private Scope getCalleeSubScope(NamedNode node, String id) {
        switch (node.getNodeEnum()) {
            case PROCEDURE_CALL:
                return this.getProcedureSubScope(id);
            case BUILD:
                return this.getBlockBlueprintScope(id);
            default:
                throw new UnexpectedNodeException(node);
        }
    }

    private Scope getProcedureSubScope(String procedureId) {
        return this.symbolTable.getSubScope(this.currentBlockScope, procedureId);
    }

    private Scope getBlockBlueprintScope(String id) {
        return this.symbolTable.getBlockScope(id).getBlueprintScope();
    }

    private void assertNotNull(Object obj) {
        if (obj == null) {
            throw new NullPointerException("The given object was null!");
        }
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
