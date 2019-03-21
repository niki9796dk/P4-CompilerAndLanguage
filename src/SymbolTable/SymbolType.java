package SymbolTable;

public class SymbolType {
    public static final SymbolType CHANNELIN = new SymbolType("channel:in","node");
    public static final SymbolType CHANNELOUT = new SymbolType("channel:out","node");
    public static final SymbolType SIZE = new SymbolType("size","non-node");
    public static final SymbolType BLOCK = new SymbolType("block","node");
    public static final SymbolType SOURCE = new SymbolType("source","node");
    public static final SymbolType OPERATION = new SymbolType("operation","node");
    public static final SymbolType NODE = new SymbolType("node");
    public static final SymbolType NONNODE = new SymbolType("non-node");

    //Make it a class hierachy so we can know both the specific type as well as the downcast?
    private String key;
    private String parent;
    SymbolType(String key) {
        this.key = key.toLowerCase();
        parent = null;
    }

    public SymbolType(String key, String parent) {
        this.key = key;
        this.parent = parent;
    }

    private String getParent() {
        return parent;
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
