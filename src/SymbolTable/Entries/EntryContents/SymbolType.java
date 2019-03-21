package SymbolTable.Entries.EntryContents;

import SymbolTable.Exceptions.NoSymbolParentException;

public class SymbolType {

    public static final SymbolType NODE = new SymbolType("node");
    public static final SymbolType NONNODE = new SymbolType("non-node");

    public static final SymbolType CHANNELIN = new SymbolType("channel:in", NODE);
    public static final SymbolType CHANNELOUT = new SymbolType("channel:out", NODE);
    public static final SymbolType BLOCK = new SymbolType("block", NODE);
    public static final SymbolType SOURCE = new SymbolType("source", NODE);
    public static final SymbolType OPERATION = new SymbolType("operation", NODE);

    public static final SymbolType SIZE = new SymbolType("size", NONNODE);
    public static final SymbolType BLUEPRINT = new SymbolType("blueprint", NONNODE);

    //Make it a class hierachy so we can know both the specific type as well as the downcast?
    private String key;
    private SymbolType parent;

    SymbolType(String key) {
        this.key = key.toLowerCase();
        parent = null;
    }

    public SymbolType(String key, SymbolType parent) {
        this.key = key;
        this.parent = parent;
    }

    private SymbolType getParent() throws NoSymbolParentException {
        if (parent != null)
            return parent;
        else throw new NoSymbolParentException("No parent for of symboltype \"" + this.getKey() + "\"");
    }

    public String getKey() {
        return key;
    }

    /*
    public boolean isUpcastOf(SymbolType that){
        String t = that.getKey();

        while (t != null && !this.getKey().equals(t))
            t = that.getParent();

        return this.getKey().equals(t);
    }

    public boolean isDowncastOf(SymbolType that){
        String t = this.getKey();

        while (t != null && !that.getKey().equals(t))
            t = this.getParent();

        return this.getKey().equals(t);
    }
    */


}
