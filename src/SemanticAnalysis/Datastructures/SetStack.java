package SemanticAnalysis.Datastructures;

import java.util.EmptyStackException;

/**
 * A set stack. An interface used for a data structure implementing both a set and a stack into a single structure.
 * The set is used to keep track of unique elements, and the stack the order.
 * An element can only be in the setStack once at a time. The order of removal is equal to the stack order.
 *
 * @param <T> The type of the setStack
 */
public interface SetStack<T> {
    /**
     * The push function. Is used to insert the element into the set, and push it onto the stack.
     *
     * @param t The element
     * @return True if the element was inserted, and false if it was not - which is caused by the element already being in the setStack.
     */
    boolean push(T t);

    /**
     * The pop method. Will pop the first element of the stack, and then remove that element from the set too.
     *
     * @return The popped element
     * @throws EmptyStackException if this stack is empty.
     */
    T pop();

    /**
     * The peek method, used to look at the top element of the stack, without popping it.
     *
     * @return The element.
     * @throws java.util.EmptyStackException if this stack is empty.
     */
    T peek();

    /**
     * Checks if the stack/set is empty or not.
     *
     * @return true if the set/stack is empty, false if is not.
     */
    boolean empty();

}
