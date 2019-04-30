package AST.TreeWalks;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;
import AST.Nodes.NodeClasses.NamedNodes.GroupNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.SelectorNode;
import AST.Nodes.NodeClasses.NamedNodes.ParamsNode;
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

    public TypeCheckerVisitor(SymbolTableInterface symbolTableInterface) {
        this.typeSystem = new TypeSystem(symbolTableInterface);
    }

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
                break;

            case GROUP:
                verifyInputType(node);
                break;

            case ASSIGN:
                AbstractNode leftNode = node.getChild();
                AbstractNode rightNode = leftNode.getSib();

                NodeEnum leftSide = this.typeSystem.getTypeOfNode(leftNode, currentBlockScope, currentSubScope);
                NodeEnum rightSide = this.typeSystem.getTypeOfNode(rightNode, currentBlockScope, currentSubScope);

                // Swap the channel types
                /*
                if (rightSide == NodeEnum.CHANNEL_OUT_MY) {
                    rightSide = NodeEnum.CHANNEL_OUT_TYPE;
                } else if (rightSide == NodeEnum.CHANNEL_IN_MY) {
                    rightSide = NodeEnum.CHANNEL_IN_TYPE;
                }
                */

                if (leftSide != rightSide) {
                    throw new TypeInconsistencyException("Assignment: " + node + " - Has different type on the left and right side of the assignment: " + leftNode + "("+ leftSide +") = " + rightNode + "("+ rightSide +")");
                }

                break;

            case BUILD:
            case PROCEDURE_CALL:
                AbstractNode calleeNode;

                switch (node.getNodeEnum()) {
                    case PROCEDURE_CALL:
                        String nodeId = ((NamedIdNode) node.findFirstChildOfClass(SelectorNode.class)).getId();
                        calleeNode = this.typeSystem.getProcedure(currentBlockScope, nodeId);
                        break;
                    case BUILD:
                        nodeId = ((NamedIdNode) node).getId();
                        boolean isNotOperation = !this.typeSystem.getSymbolTable().isPredefinedOperation(nodeId);
                        boolean isNotSource = !this.typeSystem.getSymbolTable().isPredefinedSource(nodeId);

                        if (isNotOperation && isNotSource) {
                            calleeNode = this.typeSystem.getBlock(nodeId);
                            break;
                        } else {
                            // This check is only for blocks, so return if not an block
                            return;
                        }
                    default:
                        throw new UnexpectedNodeException(node);
                }

                // Get actual and formal parameter list
                ParamsNode actualParams = (ParamsNode) node.findFirstChildOfClass(ParamsNode.class);
                ParamsNode formalParams = (ParamsNode) calleeNode.findFirstChildOfClass(ParamsNode.class);

                this.typeCheckParamLists(node, formalParams, actualParams);

                break;

                //// ACTUAL TYPE CHECKING END


            case PARAMS:
                break;


            case DRAW:
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
                break;

            default:
                throw new UnexpectedNodeException(node);
        }
    }

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

    private void typeCheckParamLists(AbstractNode node, ParamsNode formalParams, ParamsNode actualParams) {
        // Check if not params was given
        if (actualParams == null && formalParams == null) {
            // Return since everything is fine
            return;
        } else if (actualParams == null || formalParams == null) {
            // Throw an exception, since only one of the params is undefined.
            throw new ShouldNotHappenException("SHOULD NOT HAPPEN HERE - Only one param was defined!: " + node);
        } else {
            // Verify that there is the same amount of params
            if (actualParams.countChildren() != formalParams.countChildren()) {
                throw new ParamsInconsistencyException("Parameter count inconsistency: " + formalParams + " Formal[" + formalParams.countChildren() + "] vs. " + actualParams + " Actual[" + actualParams.countChildren() + "]" );
            }

            // Verify that the parameters match in type
            AbstractNode formal = formalParams.getChild();
            AbstractNode actual = actualParams.getChild();
            for (int i = 0; i < formalParams.countChildren(); i++) {
                NodeEnum formalType = this.typeSystem.getTypeOfNode(formal, currentBlockScope, currentSubScope);
                NodeEnum actualType = this.typeSystem.getTypeOfNode(actual, currentBlockScope, currentSubScope);

                if (formalType != actualType) {
                    throw new TypeInconsistencyException("Procedure call type inconsistency: " + ((NamedIdNode) formal).getId() + " - " + formalType + " vs. " + actualType);
                }

                formal = formal.getSib();
                actual = actual.getSib();
            }

            // If we reach this point, everything is cool.
        }
    }

    // TODO: Clean code: PLZZZZZZZ
    private void verifyInputType(AbstractNode node) {
        if (node instanceof GroupNode) {
            // If instance of group node, then verify all children of the group to be input types
            AbstractNode child = node.getChild();

            do {
                this.verifyInputType(child);
            } while ((child = child.getSib()) != null);

        } else {
            // If not an instance of a group node, verify that the node itself is an input type
            NodeEnum nodeType = this.typeSystem.getTypeOfNode(node, this.currentBlockScope, this.currentSubScope);

            if (nodeType == null) {
                this.throwTypeException(node);
            }
            switch (nodeType) {
                case CHANNEL_IN_MY:
                case CHANNEL_OUT_TYPE:
                case SOURCE_TYPE:
                    return; // The node was of the correct type, so return an do not throw any exceptions

                default:
                    // If the node was not a correct specific type, then test if it's a middle type node
                    verifyMiddleType(node);
            }
        }
    }

    private void verifyMiddleType(AbstractNode node) {
        NodeEnum nodeType = this.typeSystem.getTypeOfNode(node, this.currentBlockScope, this.currentSubScope);

        if (nodeType == null) {
            this.throwTypeException(node);
        }
        switch (nodeType) {
            case BLOCK_TYPE:
            case OPERATION_TYPE:
                return; // The node was of the correct type, so return an do not throw any exceptions

            default:
                throw new TypeInconsistencyException("Node: " + node.toString() + " - Was not of the correct type for it's chain placement (" + nodeType + ")");
        }
    }

    private void verifyOutputType(AbstractNode node) {
        NodeEnum nodeType = this.typeSystem.getTypeOfNode(node, this.currentBlockScope, this.currentSubScope);

        if (nodeType == null) {
            this.throwTypeException(node);
        }
        switch (nodeType) {
            case CHANNEL_OUT_MY:
            case CHANNEL_IN_TYPE:
                return; // The node was of the correct type, so return an do not throw any exceptions

            default:
                // If the node was not a correct specific type, then test if it's a middle type node
                verifyMiddleType(node);
        }
    }

    private void throwTypeException(AbstractNode node) {
        throw new TypeInconsistencyException("Node: " + node.toString() + " - Is a typeless node, and therefore cannot be placed within a chain.");
    }
}
