package SemanticAnalysis.Datastructures;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HashSetStackTest {

    // Field
    private HashSetStack<Integer> hashSetStack;

    @BeforeEach
    void beforeEach() {
        hashSetStack = new HashSetStack();
    }

    @Test
    void push01() {
        assertTrue(hashSetStack.push(1));
    }

    @Test
    void push02() {
        hashSetStack.push(1);
        assertFalse(hashSetStack.push(1));
    }

    @Test
    void pop() {
        hashSetStack.push(1);
        assertEquals(1, (int) hashSetStack.pop());
    }

    @Test
    void peek() {
        hashSetStack.push(1);
        assertEquals(1, (int) hashSetStack.peek());
    }

    @Test
    void empty01() {
        hashSetStack.push(1);
        hashSetStack.push(2);
        hashSetStack.push(3);
        assertFalse(hashSetStack.empty());
    }

    @Test
    void empty02() {
        assertTrue(hashSetStack.empty());
    }
}