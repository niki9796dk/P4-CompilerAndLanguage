package SemanticAnalysis.Datastructures;

import java.util.HashSet;
import java.util.Stack;

/**
 * A data structure which utilizes both a hash set and a stack.
 * The hashSet is used for easy look up of items, to verify that they are present in the structure.
 * The stack is used to keep track of the order, so when we want to pop an element we also know which element to remove from the set.
 * @param <T>
 */
public class HashSetStack<T> implements SetStack<T>{

    // Fields
    private HashSet<T> hashSet;
    private Stack<T> stack;

    /**
     * The constructor requires no parameters, and simply initiate both the set and stack.
     */
    public HashSetStack() {
        this.hashSet = new HashSet<>();
        this.stack = new Stack<>();
    }

    /**
     * The push method, which is used to push an element into the stack and the set.
     * @param t the element to push
     * @return true if the element was not already in the stack/set, and false if it was.
     */
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

    /**
     * The pop method, which is used to pop an element from the stack, and remove that from the set.
     * @return The popped element
     */
    @Override
    public T pop() {
        T t = this.stack.pop();
        this.hashSet.remove(t);
        return t;
    }

    /**
     * The peek method, used to look at the top element of the stack, without popping it.
     * @return The element.
     * @throws java.util.EmptyStackException  if this stack is empty.
     */
    @Override
    public T peek() {
        return this.stack.peek();
    }

    /**
     * Checks if the stack/set is empty or not.
     * @return true if the set/stack is empty, false if is not.
     */
    @Override
    public boolean empty(){
        return this.stack.empty();
    }
}
