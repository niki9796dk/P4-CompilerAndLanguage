package SymbolTable.Entries.EntryContents;

import SymbolTable.Exceptions.NoSymbolParentException;

public class SymbolType {

    public static final SymbolType NODE = new SymbolType();
    public static final SymbolType NONNODE = new SymbolType();

    public static final SymbolType CHANNELIN = new SymbolType(NODE);
    public static final SymbolType CHANNELOUT = new SymbolType(NODE);
    public static final SymbolType BLOCK = new SymbolType(NODE);
    public static final SymbolType SOURCE = new SymbolType(NODE);
    public static final SymbolType OPERATION = new SymbolType(NODE);

    public static final SymbolType SIZE = new SymbolType(NONNODE);
    public static final SymbolType BLUEPRINT = new SymbolType(NONNODE);

    //Make it a class hierachy so we can know both the specific type as well as the downcast?
    private SymbolType parent;

    SymbolType() {
        parent = null;
    }

    public SymbolType(SymbolType parent) {
        this.parent = parent;
    }

    private SymbolType getParent() throws NoSymbolParentException {
        if (parent != null)
            return parent;
        else throw new NoSymbolParentException("No parent for of symboltype");
    }

}
