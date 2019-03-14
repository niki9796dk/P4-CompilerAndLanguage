import java.util.Objects;

public class DoublePair {
    public final Double first;
    public final Double second;

    public DoublePair(Double first, Double second) {
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
        return "DoublePair{" +
                "first=" + first +
                ", second=" + second +
                '}';
    }
}
