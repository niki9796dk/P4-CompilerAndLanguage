package SymbolTableImplementation;

import AST.Nodes.AbstractNodes.Node;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;
import AST.Nodes.NodeClasses.NamedNodes.NamedIdNodes.DummyNode;
import AST.Nodes.NodeClasses.NamedNodes.SizeNode;
import Enums.AnsiColor;

public class VariableEntryOrDefault extends VariableEntry {
    private NamedIdNode defaultSubtype;

    /**
     * @param node Create a new variable entry for a specific NamedIdNode
     */
    public VariableEntryOrDefault(NamedIdNode node) {
        super(node);
        this.setDefaultSubtype(new DummyNode());
    }

    public void setDefaultSubtype(Node defaultSubtype) {
        if (defaultSubtype instanceof NamedIdNode) {
            this.defaultSubtype = (NamedIdNode) defaultSubtype;
        } else {
            throw new IllegalArgumentException("Node has to be a named id node: " + defaultSubtype);
        }
    }

    @Override
    public NamedIdNode getSubType(int number) {
        NamedIdNode subType = super.getSubType(number);

        return (subType != null) ? subType : this.defaultSubtype;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append(super.toString())
                .append("\t\t\t\t\t")
                .append("Default: ")
                .append(AnsiColor.YELLOW).append(this.defaultSubtype.getId()).append(AnsiColor.RESET)
                .append(" | Node: ")
                .append(AnsiColor.YELLOW).append(this.defaultSubtype).append(AnsiColor.RESET)
                .append("\n");

        return builder.toString();
    }
}
