package SymbolTable;

import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNode;

public interface SymbolTableInterface {
    void openBlockScope(NamedNode node);           // Adds a block table to the symbol table
    void openSubScope(NamedNode node);             // Opens a sub scope within the latest block scope

    BlockScope getBlockScope(String id);           // Gets a specific BlockScope from an id
    BlockScope getLatestBlockScope();              // Gets the latest block scope

    void insertVariable(NamedNode node);           // Inserts a variable into the most recent scope
    void reassignVariable(NamedNode assignNode);   // Updates a variable; Triggered by an assign

    boolean isPredefinedOperation(String operation);
}
