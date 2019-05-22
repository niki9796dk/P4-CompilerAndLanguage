package SemanticAnalysis.Datastructures;

public class Connection<T, K> {
    T from;
    K to;

    public Connection(T from, K to) {
        this.from = from;
        this.to = to;
    }

    public T getFrom() {
        return from;
    }

    public K getTo() {
        return to;
    }
}
