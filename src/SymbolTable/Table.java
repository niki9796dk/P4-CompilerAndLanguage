package SymbolTable;

import AST.AbstractNode;

public interface Table {
    ScopeEntry getNewEntry();
}
