package SymbolTableImplementation;

import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.BlockNode;

/**
 * The essential methods for any symboltable implementation.
 * The responsibility is indexing the symbols throughout the compiler phases.
 */
public interface SymbolTableInterface {

    /**
     * Adds a block table to the symbol table
     *
     * @param node The node to add to the symbol table.
     */
    void openBlockScope(BlockNode node);

    /**
     * Opens a sub scope within the latest block scope
     *
     * @param node The subnode to open within the most recent block scope.
     */
    void openSubScope(NamedNode node);

    /**
     * Gets a specific BlockScope from an id
     *
     * @param id The id of the desired blockscope.
     * @return The blockscope with the id, or Null if no such blockscope exists.
     */
    BlockScope getBlockScope(String id);


    /**
     * Returns the latest block scope
     *
     * @return the most recently added block scope, or Null if no such blockscope exists.
     */
    BlockScope getLatestBlockScope();


    /**
     * Inserts a variable into the most recent scope
     *
     * @param node The new node to insert into the symbol table.
     */
    void insertVariable(NamedNode node);

    /**
     * Updates a variable by reassigning it. This is triggered by assigning to an already declared variable.
     *
     * @param assignNode The variable to reassign.
     */
    void reassignVariable(NamedNode assignNode);

    /**
     * Check if an operation keyword is a one of the final predefined operations.
     *
     * @param operation The operation Keyword
     * @return if the operation is valid.
     */
    boolean isPredefinedOperation(String operation);
}
