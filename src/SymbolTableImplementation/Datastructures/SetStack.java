package SymbolTableImplementation.Datastructures;

public interface SetStack<T> {
    boolean push(T t);
    T pop();
    T peek();
    boolean empty();

}
