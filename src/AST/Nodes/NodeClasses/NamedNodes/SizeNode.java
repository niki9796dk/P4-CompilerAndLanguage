package AST.Nodes.NodeClasses.NamedNodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNode;
import AST.Nodes.AbstractNodes.Nodes.AbstractNodes.NumberedNodes.NamedNodes.NamedIdNode;

import java.util.Objects;

/**
 * A deceleration of a size variable. Contains the specified properties.
 */
public class SizeNode extends NamedIdNode {
    public final Integer first;
    public final Integer second;

    public SizeNode(Integer first, Integer second) {
        super("Size", "[" + first + ", " + second + "]", NodeEnum.SIZE);
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SizeNode that = (SizeNode) o;
        return Objects.equals(first, that.first) &&
                Objects.equals(second, that.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
