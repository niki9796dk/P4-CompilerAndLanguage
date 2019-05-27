package SymbolTableImplementation;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;
import Enums.AnsiColor;

import java.util.LinkedList;
import java.util.List;

/**
 * A scope entry representing a variable.
 */
public class VariableEntry {
    protected NamedIdNode node;
    protected List<NamedIdNode> subTypes;

    /**
     * @param node Create a new variable entry for a specific NamedIdNode
     */
    public VariableEntry(NamedIdNode node) {
        this.node = node;
        this.subTypes = new LinkedList<>();
    }

    /**
     * Retrieves the id of the Variable Entry, which is the id of the node it represents.
     *
     * @return The id of the node as a string.
     */
    public String getId() {
        return this.getNode().getId();
    }

    /**
     * Retrieve the appropriate enum for the variable entry's node.
     *
     * @return The Enum representing the node
     */
    public NodeEnum getSuperType() {
        return this.getNode().getNodeEnum();
    }

    /**
     * Returns the correct node for a certain node number. This is useful for when a variable is reassigned, and the result appropriate for a specific node is required.
     * In simple terms; A nodes number is used connect find the correct assignment of the variable at that time.
     *
     * @param number The node number of the current point of the node result request.
     * @return The result of the variable assigned at that point, or null if it is unassigned.
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
     * @return the node for the variable entry.
     */
    public NamedIdNode getNode() {
        return node;
    }

    /**
     * @param subNode Add a new assignment of the variable.
     */
    public void setSubType(NamedIdNode subNode) {
        this.subTypes.add(subNode);
    }

    /**
     * The to string method.
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return "\t\t\tEntry: " + AnsiColor.YELLOW + this.getId() + AnsiColor.RESET
                + " | SuperType: " + AnsiColor.PURPLE + this.getSuperType() + AnsiColor.RESET
                + " | Node: " + AnsiColor.PURPLE + this.getNode() + AnsiColor.RESET
                + "\n" + getSubTypeToString();
    }

    /**
     * A helper function for the toString method, which returns subtype part of the toString method.
     *
     * @return A string representation of the subType list.
     */
    protected String getSubTypeToString() {
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
