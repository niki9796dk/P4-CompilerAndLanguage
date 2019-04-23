package AST.TreeWalks;

import AST.Nodes.AbstractNodes.Nodes.AbstractNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.BlockNode;
import AST.TreeWalks.Exceptions.ScopeBoundsViolationException;
import AST.Visitor;
import SymbolTableImplementation.Scope;
import SymbolTableImplementation.SymbolTable;
import SymbolTableImplementation.SymbolTableInterface;
import SymbolTableImplementation.VariableEntry;

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
                this.verifyInput(childNode);

                // Loop all middle children and verify them as an element node
                childNode = childNode.getSib();
                while (childNode.getSib() != null) {
                    this.verifyElement(childNode);
                    childNode = childNode.getSib();
                }

                // Verify the last node as an output node
                this.verifyOutput(childNode);
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

    private void verifyInput(AbstractNode node) {

    }

    private void verifyElement(AbstractNode node) {

    }

    private void verifyOutput(AbstractNode node) {

    }
}
