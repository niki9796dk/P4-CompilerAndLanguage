package AST;

import java.util.Objects;

public class DoublePair extends AbstractNode {
    private String name;
    public final Integer first;
    public final Integer second;

    public DoublePair(Integer first, Integer second) {
        this.name = "Size";
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DoublePair that = (DoublePair) o;
        return Objects.equals(first, that.first) &&
                Objects.equals(second, that.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }

    @Override
    public String toString() {
        return name + " [" + first + " " + second + "]";
    }
}
