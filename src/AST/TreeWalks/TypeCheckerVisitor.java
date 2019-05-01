package AST.TreeWalks;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;
import AST.Nodes.NodeClasses.NamedNodes.GroupNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.BuildNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.SelectorNode;
import AST.Nodes.NodeClasses.NamedNodes.ParamsNode;
import AST.Nodes.NodeClasses.NamedNodes.ProcedureCallNode;
import AST.TreeWalks.Exceptions.UnexpectedNodeException;
import AST.Visitor;
import SymbolTableImplementation.BlockScope;
import SymbolTableImplementation.SymbolTableInterface;
import TypeChecker.Exceptions.ParamsInconsistencyException;
import TypeChecker.Exceptions.ShouldNotHappenException;
import TypeChecker.Exceptions.TypeInconsistencyException;
import TypeChecker.TypeSystem;

public class TypeCheckerVisitor implements Visitor {

    private TypeSystem typeSystem;
    private String currentBlockScope;
    private String currentSubScope;

    /**
     * Type checker visitor constructor
     * @param symbolTableInterface A symbol table used as reference.
     */
    public TypeCheckerVisitor(SymbolTableInterface symbolTableInterface) {
        this.typeSystem = new TypeSystem(symbolTableInterface);
    }

    /**
     * The pre order walk type checking of the abstract syntax tree.
     * @param printLevel (NOT USED) the level, used to decide how many indents there should be in the print statement.
     * @param abstractNode The node to type check.
     */
    @Override
    public void pre(int printLevel, AbstractNode abstractNode) {
        NamedNode node = (NamedNode) abstractNode;

        switch (node.getNodeEnum()) {
            // Location enums
            case BLOCK:
                this.currentBlockScope = ((NamedIdNode) node).getId();
                break;
            case BLUEPRINT:
                this.currentSubScope = BlockScope.BLUEPRINT;
                break;
            case PROCEDURE:
                this.currentSubScope = BlockScope.PROCEDURE_PREFIX + ((NamedIdNode) node).getId();
                break;
            case CHANNEL_DECLARATIONS:
                this.currentSubScope = BlockScope.CHANNELS;
                break;

                //// ACTUAL TYPE CHECKING START

            case CHAIN:
                this.typeCheckChain(node);
                break;

            case GROUP:
                this.verifyInputType(node);
                break;

            case ASSIGN:
                this.typecheckAssignments(node);
                break;

            case BUILD:
            case PROCEDURE_CALL:
                this.typecheckBuildAndProcCalls(node);
                break;

            //// ACTUAL TYPE CHECKING END

            case PARAMS:
            case DRAW:  // TODO: Maybe we should check whatever we draw is a block... But this should also be checked in the scope checker
            case SIZE:
            case SELECTOR:
            case ROOT:
            case CHANNEL_IN_MY:
            case CHANNEL_OUT_MY:
            case CHANNEL_IN_TYPE:
            case CHANNEL_OUT_TYPE:
            case SIZE_TYPE:
            case BLOCK_TYPE:
            case SOURCE_TYPE:
            case BLUEPRINT_TYPE:
            case OPERATION_TYPE:
                // Do nothing
                break;

            default:
                throw new UnexpectedNodeException(node);
        }
    }

    /**
     * The post order walk type checking of the abstract syntax tree.
     * @param printLevel (NOT USED) The level, used to decide how many indents there should be in the print statement.
     * @param abstractNode The node to type check
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
            case BUILD:
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

            default:
                throw new UnexpectedNodeException(node);
        }
    }

    /**
     * Method used to type check an assignment node,
     * @param node The assignment node to typecheck
     */
    private void typecheckAssignments(AbstractNode node) {
        AbstractNode leftNode = node.getChild();
        AbstractNode rightNode = leftNode.getSib();

        this.typeSystem.assertEqualTypes(leftNode, rightNode, this.currentBlockScope, this.currentSubScope);
    }

    /**
     * Method used to typecheck build and procedure call nodes.
     * @param callerNode The build / procedure call node to type check.
     */
    private void typecheckBuildAndProcCalls(NamedNode callerNode) {
        AbstractNode calleeNode = this.findCalleeBlockNodeFromCaller(callerNode);

        // If the callee node is null, just return, since this means that we were building something else than a block (eg. source / operation)
        boolean calleeIsNotBlock = calleeNode == null;
        if (calleeIsNotBlock) {
            return;
        }

        // Get actual and formal parameter list
        ParamsNode actualParams = (ParamsNode) callerNode.findFirstChildOfClass(ParamsNode.class);
        ParamsNode formalParams = (ParamsNode) calleeNode.findFirstChildOfClass(ParamsNode.class);

        // Compare param lists
        this.typeCheckParamLists(callerNode, formalParams, actualParams);
    }

    /**
     * Finds and returns the callee node from a procedure call or build statement.
     * @param callerNode The ProcedureCallNode / BuildNode caller who's callee we want to find.
     * @return (AbstractNode|null) The callee node, either an BlockNode or an ProcedureNode depending on the caller node type.
     * Returns null if the caller node is not of type build or procedure call.
     */
    private AbstractNode findCalleeBlockNodeFromCaller(NamedNode callerNode) {

        if (callerNode instanceof ProcedureCallNode) {
            // Get the callee ID
            String nodeId = ((NamedIdNode) callerNode.findFirstChildOfClass(SelectorNode.class)).getId();

            return this.typeSystem.getProcedure(currentBlockScope, nodeId);

        } else if (callerNode instanceof BuildNode) {
            // Get the callee ID
            String nodeId = ((NamedIdNode) callerNode).getId();

            // Verify that we are building an block, and not anything else. TODO: How about operation and source params?
            boolean isNotOperation = !this.typeSystem.getSymbolTable().isPredefinedOperation(nodeId);
            boolean isNotSource = !this.typeSystem.getSymbolTable().isPredefinedSource(nodeId);

            // Return the block node, if the callee is a block
            if (isNotOperation && isNotSource) {
                return this.typeSystem.getBlock(nodeId);
            }
        }

        // Return null if the caller node is of wrong instance, or not a block
        return null;
    }

    /**
     * The method used to type check a chain.
     * The method will iterate all the elements of the chain, and check that the node is allowed in it's given position.
     * @param node The chain node to type check.
     */
    private void typeCheckChain(AbstractNode node) {
        // Get first child
        AbstractNode childNode = node.getChild();

        // Verify it's type as an input node
        this.verifyInputType(childNode);

        // Loop all middle children and verify them as an element node
        childNode = childNode.getSib();
        while (childNode.getSib() != null) {
            this.verifyMiddleType(childNode);
            childNode = childNode.getSib();
        }

        // Verify the last node as an output node
        this.verifyOutputType(childNode);
    }

    /**
     * Method used to verify that two parameter lists all contain the same amount of elements and that they match in type.
     * @param node The caller node, from which the parameters lists was extracted - Only used for error messages.
     * @param formalParams The formal parameter node
     * @param actualParams The actual parameter node
     */
    private void typeCheckParamLists(AbstractNode node, ParamsNode formalParams, ParamsNode actualParams) {
        // Evaluate states
        boolean actualParamsIsNull = actualParams == null;
        boolean formalParamsIsNull =  formalParams == null;

        boolean callerAndCalleeBothHaveParams = !(actualParamsIsNull || formalParamsIsNull);    // Both false
        boolean callerOrCalleeIsMissingParams = actualParamsIsNull ^ formalParamsIsNull;        // XOR

        if (callerAndCalleeBothHaveParams) {
            // Verify that there is the same amount of params
            this.assertEqualParameterCount(actualParams, formalParams);

            // Verify that the parameters match in type
            AbstractNode formal = formalParams.getChild();
            AbstractNode actual = actualParams.getChild();
            for (int i = 0; i < formalParams.countChildren(); i++) {
                // Assert equals
                this.typeSystem.assertEqualTypes(actual, formal, currentBlockScope, currentSubScope, "Procedure call type inconsistency");

                // Update node pointers to the next sib.
                formal = formal.getSib();
                actual = actual.getSib();
            }

        } else if (callerOrCalleeIsMissingParams) {
            // Throw an exception, since one of the params is undefined.
            throw new ShouldNotHappenException("Only one param was defined!: " + node);

        }
        // else {
            // No params was given and nor was any expected.
            // Simply dont do anything then.
        // }
    }

    /**
     * Verify that a node is allowed as an input type node.
     * @param node The node to verify
     */
    private void verifyInputType(AbstractNode node) {
        if (node instanceof GroupNode) {
            // If instance of group node, then verify all children of the group to be input types
            AbstractNode child = node.getChild();

            do {
                this.verifyInputType(child);
            } while ((child = child.getSib()) != null);

        } else {
            // If not an instance of a group node, verify that the node itself is an input type
            NodeEnum nodeType = this.getTypeOfNode(node);

            switch (nodeType) {
                case CHANNEL_IN_MY:
                case CHANNEL_OUT_TYPE:
                case SOURCE_TYPE:
                    return; // The node was of the correct type, so return an do not throw any exceptions

                default:
                    // If the node was not a correct specific type, then test if it's a middle type node
                    this.verifyMiddleType(node);
            }
        }
    }

    /**
     * Verify that a node is allowed as an middle type node.
     * @param node The node to verify
     */
    private void verifyMiddleType(AbstractNode node) {
        NodeEnum nodeType = this.getTypeOfNode(node);

        switch (nodeType) {
            case BLOCK_TYPE:
            case OPERATION_TYPE:
                return; // The node was of the correct type, so return an do not throw any exceptions

            default:
                throw new TypeInconsistencyException("Node: " + node.toString() + " - Was not of the correct type for it's chain placement (" + nodeType + ")");
        }
    }

    /**
     * Verify that a node is allowed as an output type node.
     * @param node The node to verify
     */
    private void verifyOutputType(AbstractNode node) {
        // Get the type of the node, and verify that it is no a typeless node.
        NodeEnum nodeType = this.getTypeOfNode(node);

        // Check the type.
        switch (nodeType) {
            case CHANNEL_OUT_MY:
            case CHANNEL_IN_TYPE:
                return; // The node was of the correct type, so return and do not throw any exceptions

            default:
                // If the node was not a correct specific type, then test if it's a middle type node
                this.verifyMiddleType(node);
        }
    }

    /**
     * Assert that two parameter nodes have the same amount of parameter children, else throw an exception
     * @param actualParams The actual params
     * @param formalParams The formal params
     * @throws ParamsInconsistencyException Is thrown if there is a size mismatch in the param nodes.
     */
    private void assertEqualParameterCount(ParamsNode actualParams, ParamsNode formalParams) {
        if (actualParams.countChildren() != formalParams.countChildren()) {
            throw new ParamsInconsistencyException("Parameter count inconsistency: " + formalParams + " Formal[" + formalParams.countChildren() + "] vs. " + actualParams + " Actual[" + actualParams.countChildren() + "]" );
        }
    }

    /**
     * Returns the type of a given node, by sending it through the type system.
     * @param node The node to extract the type of
     * @return The type of the given node
     * @throws TypeInconsistencyException Is thrown if the node is a typeless node.
     */
    private NodeEnum getTypeOfNode(AbstractNode node) {
        NodeEnum type = this.typeSystem.getTypeOfNode(node, this.currentBlockScope, this.currentSubScope);
        this.assertNonNullType(node, type);

        return type;
    }

    /**
     * Asserts that a given type is not null, and if it is an exception is thrown.
     * @param node The node the type is extracted from.
     * @param type The type extracted from the node.
     * @throws TypeInconsistencyException Is thrown if the node is a typeless node.
     */
    private void assertNonNullType(AbstractNode node, NodeEnum type) {
        if (type == null) {
            throw new TypeInconsistencyException("Node: " + node.toString() + " - Is a typeless node, and therefore cannot be placed within a chain.");
        }
    }
}
