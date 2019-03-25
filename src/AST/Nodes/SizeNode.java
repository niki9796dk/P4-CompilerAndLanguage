package AST.Nodes;

import AST.Enums.NodeEnum;
import AST.Nodes.AbstractNodes.NamedNode;

import java.util.Objects;

public class SizeNode extends NamedNode {
    public final Integer first;
    public final Integer second;

    public SizeNode(Integer first, Integer second) {
        super("Size", NodeEnum.SIZE);
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
        return super.toString() + " [" + first + ", " + second + "]";
    }
}
