package AST.TreeWalks;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;
import AST.Nodes.NodeClasses.NamedNodes.GroupNode;
import AST.TreeWalks.Exceptions.UnexpectedNodeException;
import AST.Visitor;
import SymbolTableImplementation.BlockScope;
import SymbolTableImplementation.SymbolTable;
import SymbolTableImplementation.SymbolTableInterface;
import TypeChecker.TypeInconsistencyException;
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

                if (leftSide != rightSide) {
                    throw new TypeInconsistencyException("Assignment: " + node + " - Has different type on the left and right side of the assignment: " + leftNode + " = " + rightNode);
                }

                break;

                //// ACTUAL TYPE CHECKING END

            case PROCEDURE_CALL:
            case PARAMS:
            case SELECTOR:
            case DRAW:
            case BUILD:
            case SIZE:
                break;


            case ROOT:
            case CHANNEL_IN:
            case CHANNEL_OUT:
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
            case CHANNEL_IN:
            case CHANNEL_OUT:
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

            switch (nodeType) {
                case CHANNEL_IN:
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

        switch (nodeType) {
            case BLOCK_TYPE:
            case OPERATION_TYPE:
                return; // The node was of the correct type, so return an do not throw any exceptions

            default:
                throw new TypeInconsistencyException("Node: " + node.toString() + " - Was not of the correct type for it's chain placement");
        }
    }

    private void verifyOutputType(AbstractNode node) {
        NodeEnum nodeType = this.typeSystem.getTypeOfNode(node, this.currentBlockScope, this.currentSubScope);

        switch (nodeType) {
            case CHANNEL_OUT:
            case CHANNEL_IN_TYPE:
                return; // The node was of the correct type, so return an do not throw any exceptions

            default:
                // If the node was not a correct specific type, then test if it's a middle type node
                verifyMiddleType(node);
        }
    }
}
