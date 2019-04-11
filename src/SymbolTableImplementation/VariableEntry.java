package SymbolTableImplementation;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;
import Enums.AnsiColor;

import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class VariableEntry {
    private NamedIdNode node;
    private List<NamedIdNode> subTypes;

    /**
     * @param node
     */
    public VariableEntry(NamedIdNode node) {
        this.node = node;
        this.subTypes = new LinkedList<>();
    }

    /**
     * @return
     */
    public String getId() {
        return this.getNode().getId();
    }

    /**
     * @return
     */
    public NodeEnum getSuperType() {
        return this.getNode().getNodeEnum(); // TODO: Ændre til en anden klasse af enums... Så vi også kan have primitive med.
    }

    /**
     * @param number
     * @return
     */
    // Returns null if unassigned
    public NamedIdNode getSubType(int number) {
        NamedIdNode last = null;
        for (NamedIdNode node : subTypes) {
            if (node.getNumber() > number) break;
            last = node;
        }
        return last;
    }

    /**
     * @return
     */
    public NamedIdNode getNode() {
        return node;
    }

    /**
     * @param subNode
     */
    public void setSubType(NamedIdNode subNode) {
        this.subTypes.add(subNode);
    }

    @Override
    public String toString() {
        return "\t\t\tEntry: " + AnsiColor.YELLOW + this.getId() + AnsiColor.RESET
                + " | SuperType: " + AnsiColor.PURPLE + this.getSuperType() + AnsiColor.RESET
                + " | Node: " + AnsiColor.PURPLE + this.getNode() + AnsiColor.RESET
                + "\n" + getSubTypeToString();
    }

    private String getSubTypeToString() {
        StringBuilder builder = new StringBuilder();

        if (this.subTypes.size() == 0) {
            builder.append("\t\t\t\t\t")
                    .append("Subtype: ")
                    .append(AnsiColor.RED).append("NONE").append(AnsiColor.RESET)
                    .append(" | Node: ")
                    .append(AnsiColor.RED).append("NONE").append(AnsiColor.RESET)
                    .append("\n");
        }

        for (NamedIdNode node : this.subTypes) {
            builder.append("\t\t\t\t\t")
                    .append("Subtype: ")
                    .append(AnsiColor.PURPLE).append(node.getId()).append(AnsiColor.RESET)
                    .append(" | Node: ")
                    .append(AnsiColor.PURPLE).append(node).append(AnsiColor.RESET)
                    .append("\n");
        }

        return builder.toString();
    }
}
