package AST.TreeWalks;

import AST.Nodes.AbstractNodes.Nodes.AbstractNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.BlockNode;
import AST.Visitor;
import CompilerExceptions.ScopeExceptions.ScopeBoundsViolationException;
import CompilerExceptions.ScopeExceptions.VariableAlreadyDeclaredException;
import CompilerExceptions.UnexpectedNodeException;
import SymbolTableImplementation.Scope;
import SymbolTableImplementation.SymbolTable;
import SymbolTableImplementation.SymbolTableInterface;
import SymbolTableImplementation.VariableEntry;

public class SymbolTableVisitor implements Visitor {

    private SymbolTableInterface symbolTableInterface = new SymbolTable();

    /**
     * A getter for the generated symbol table.
     *
     * @return The generated symbol table instance.
     */
    public SymbolTableInterface getSymbolTableInterface() {
        return symbolTableInterface;
    }

    /**
     * @param printLevel   the level, used to decide how many indents there should be in the print statement.
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
            case ASSIGN:
            case SIZE:
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
                checkIfVariableIsDefined(((NamedIdNode) node));
                symbolTableInterface.insertVariable(node);
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
            case BLUEPRINT_TYPE:
            case CHANNEL_DECLARATIONS:
                // Not relevant
                break;
            case SOURCE_TYPE:
            case OPERATION_TYPE:
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
     * Checks if a variable is already defined within the symbol table, and if it is, and exception is thrown.
     *
     * @param node The node with the ID to check
     * @throws ScopeBoundsViolationException thrown if the variable id is already declared.
     */
    private void checkIfVariableIsDefined(NamedIdNode node) {
        Scope latestSubScope = this.symbolTableInterface.getLatestBlockScope().getLatestSubScope();
        VariableEntry variable = latestSubScope.getVariable(node.getId());
        if (variable != null) {
            throw new VariableAlreadyDeclaredException(node, "Variable \"" + node.getId() + "\" already declared in scope: " + latestSubScope.getId());
        }
    }
}
