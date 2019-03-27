package SymbolTable.New;

import AST.Nodes.AbstractNodes.NamedNode;

public interface SymbolTableInterface {
    void openBlockScope(NamedNode node);           // Adds a block table to the symbol table
    void openSubScope(NamedNode node);             // Opens a sub scope within the latest block scope.

    void insertVariable(NamedNode node);           // Inserts a variable into the most recent scope
    void reassignVariable(NamedNode assignNode);   // Updates a variable; Triggered by an assign.
}
