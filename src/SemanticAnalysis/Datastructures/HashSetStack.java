package SemanticAnalysis.Datastructures;

import java.util.HashSet;
import java.util.Stack;

public class HashSetStack<T> implements SetStack<T>{
    private HashSet<T> hashSet;
    private Stack<T> stack;

    //
    public HashSetStack() {
        this.hashSet = new HashSet<>();
        this.stack = new Stack<>();
    }

    @Override
    public boolean push(T t) {
        if (this.hashSet.contains(t)){
            return false;
        } else {
            this.hashSet.add(t);
            this.stack.push(t);
            return true;
        }
    }

    @Override
    public T pop() {
        T t = this.stack.pop();
        this.hashSet.remove(t);
        return t;
    }

    @Override
    public T peek() {
        return this.stack.peek();
    }

    @Override
    public boolean empty(){
        return this.stack.empty();
    }
}
