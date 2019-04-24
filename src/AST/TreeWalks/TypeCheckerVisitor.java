package AST.TreeWalks;

import AST.Nodes.AbstractNodes.Nodes.AbstractNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNode;
import AST.Visitor;
import SymbolTableImplementation.SymbolTable;
import SymbolTableImplementation.SymbolTableInterface;

public class TypeCheckerVisitor implements Visitor {

    private SymbolTableInterface symbolTableInterface = new SymbolTable();

    public SymbolTableInterface getSymbolTableInterface() {
        return symbolTableInterface;
    }

    @Override
    public void pre(int printLevel, AbstractNode abstractNode) {
        NamedNode node = (NamedNode) abstractNode;

        switch (node.getNodeEnum()) {
            // No action enums
            case ROOT:
            case GROUP:
                break;

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
            case SIZE_TYPE:
            case BLOCK_TYPE:
            case SOURCE_TYPE:
            case BLUEPRINT_TYPE:
            case OPERATION_TYPE:

            default:
                throw new RuntimeException("Unexpected Node");
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
            case SIZE_TYPE:
            case BLOCK_TYPE:
            case SOURCE_TYPE:
            case BLUEPRINT_TYPE:
            case OPERATION_TYPE:

            default:
                throw new RuntimeException("Unexpected Node");
        }
    }

    private void verifyInputType(AbstractNode node) {

    }

    private void verifyMiddleType(AbstractNode node) {

    }

    private void verifyOutputType(AbstractNode node) {

    }
}
