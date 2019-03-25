package SymbolTable.New;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.NamedIdNode;
import AST.Nodes.AbstractNodes.NamedNode;

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
        return "\t\t\tEntry: " + this.getId() + " -> " + this.getSuperType() + "_" + this.getSubType() + " #" + this.getNode();
    }
}
