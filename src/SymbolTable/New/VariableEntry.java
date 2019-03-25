package SymbolTable.New;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.NamedIdNode;
import AST.Nodes.AbstractNodes.NamedNode;
import Enums.AnsiColor;

public class VariableEntry {
    private String subType;
    private NamedIdNode node;

    public VariableEntry(NamedIdNode node) {
        this.node = node;
        this.subType = "UNKNOWN";       // TODO: Gør bedre?
    }

    public String getId() {
        return this.getNode().getId();
    }

    public NodeEnum getSuperType() {
        return this.getNode().getNodeEnum(); // TODO: Ændre til en anden klasse af enums... Så vi også kan have primitive med.
    }

    public String getSubType() {
        return subType;
    }

    public NamedIdNode getNode() {
        return node;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    @Override
    public String toString() {
        return "\t\t\tEntry: "      + AnsiColor.YELLOW  +   this.getId()         +  AnsiColor.RESET
                + " | SuperType: "  + AnsiColor.PURPLE  +   this.getSuperType()  +  AnsiColor.RESET
                + " | SubType: "    + AnsiColor.PURPLE  +   this.getSubType()    +  AnsiColor.RESET
                + " | Node: "       + AnsiColor.PURPLE  +   this.getNode()       +  AnsiColor.RESET;
    }
}
