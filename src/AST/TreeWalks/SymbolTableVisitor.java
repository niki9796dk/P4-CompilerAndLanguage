package AST.TreeWalks;

import AST.Nodes.AbstractNodes.Nodes.AbstractNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.BlockNode;
import AST.TreeWalks.Exceptions.ScopeBoundsViolationException;
import AST.TreeWalks.Exceptions.UnexpectedNodeException;
import AST.Visitor;
import SymbolTableImplementation.Scope;
import SymbolTableImplementation.SymbolTableInterface;
import SymbolTableImplementation.SymbolTable;
import SymbolTableImplementation.VariableEntry;

public class SymbolTableVisitor implements Visitor {

    private SymbolTableInterface symbolTableInterface = new SymbolTable();

    public SymbolTableInterface getSymbolTableInterface() {
        return symbolTableInterface;
    }

    /**
     * @param printLevel   the level, used connect decide how many indents there should be in the print statement.
     * @param abstractNode The node which is being visited.
     */
    @Override
    public void pre(int printLevel, AbstractNode abstractNode) {
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
                symbolTableInterface.openBlockScope((BlockNode) node);
                break;

                // Open sub scopes
            case BLUEPRINT:
            case PROCEDURE:
            case CHANNEL_DECLARATIONS:
                symbolTableInterface.openSubScope(node);
                break;

                // Insert variables
            case CHANNEL_IN_MY:
            case CHANNEL_OUT_MY:
            case CHANNEL_IN_TYPE:
            case CHANNEL_OUT_TYPE:
            case SIZE_TYPE:
            case BLOCK_TYPE:
            case SOURCE_TYPE:
            case BLUEPRINT_TYPE:
            case OPERATION_TYPE:
                checkIfVariableIsDefined(((NamedIdNode) node).getId());
                symbolTableInterface.insertVariable(node);
                break;

            default:
                throw new UnexpectedNodeException(node);
        }
    }

    /**
     * @param printLevel   the level, used connect decide how many indents there should be in the print statement.
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
                // Not relevant
                break;

                // Im special
            case ASSIGN:
                symbolTableInterface.reassignVariable(node);
                break;

            default:
                throw new UnexpectedNodeException(node);
        }
    }

    /**
     * Checks latest sub scope for a variable with identifier id, otherwise throw ScopeBoundsViolationException
     * @param id Identifier of variable to check
     */
    private void checkIfVariableIsDefined(String id) {
        Scope latestSubScope = this.symbolTableInterface.getLatestBlockScope().getLatestSubScope();
        VariableEntry variable = latestSubScope.getVariable(id);
        if (variable != null) {
            throw new ScopeBoundsViolationException("Variable \"" + id + "\" already declared in scope: " + latestSubScope.getId());
        }
    }
}
