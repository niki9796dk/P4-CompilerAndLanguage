package AST.TreeWalks;

import AST.Nodes.AbstractNodes.AbstractNode;
import AST.Nodes.AbstractNodes.NamedNode;
import AST.Visitor;
import SymbolTable.New.SymbolTableInterface;
import SymbolTable.Tables.RootTable;

public class SymbolTableVisitor implements Visitor {

    private RootTable rootTable;
    private SymbolTableInterface symbolTableInterface;

    @Override
    public void pre(int i, AbstractNode abstractNode) {
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
                // Not relevant
                break;

                // Open block scope
            case BLOCK:
                symbolTableInterface.openBlockScope(node);
                break;

                // Open sub scopes
            case BLUEPRINT:
            case PROCEDURE:
            case CHANNEL_DECLARATIONS:
                symbolTableInterface.openSubScope(node);
                break;

                // Insert variables
            case CHANNEL_IN:
            case CHANNEL_OUT:
            case SIZE_TYPE:
            case BLOCK_TYPE:
            case SOURCE_TYPE:
            case BLUEPRINT_TYPE:
            case OPERATION_TYPE:
                symbolTableInterface.insertVariable(node);
                break;

            default:
                throw new RuntimeException("Unexpected Node");
        }
    }

    @Override
    public void post(int i, AbstractNode abstractNode) {
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
            case BLOCK:
            case BLUEPRINT:
            case PROCEDURE:
            case CHANNEL_IN:
            case CHANNEL_OUT:
            case SIZE_TYPE:
            case BLOCK_TYPE:
            case SOURCE_TYPE:
            case BLUEPRINT_TYPE:
            case OPERATION_TYPE:
            case CHANNEL_DECLARATIONS:
                // Not relevant
                break;

                // Im special
            case ASSIGN:
                symbolTableInterface.reassignVariable(node);
                break;

            default:
                throw new RuntimeException("Unexpected Node");
        }
    }
}
