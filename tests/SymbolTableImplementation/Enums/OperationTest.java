package SymbolTableImplementation.Enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OperationTest {

    @Test
    void isValid() {
        assertTrue(Operation.isValid("Addition"));
        assertTrue(Operation.isValid("Multiplication"));
        assertTrue(Operation.isValid("Subtraction"));
        assertTrue(Operation.isValid("_Addition"));
        assertTrue(Operation.isValid("_Multiplication"));
        assertTrue(Operation.isValid("_Subtraction"));
        assertTrue(Operation.isValid("_Division"));
        assertTrue(Operation.isValid("_Sigmoid"));
        assertTrue(Operation.isValid("_Tanh"));
        assertTrue(Operation.isValid("_Relu"));
        assertTrue(Operation.isValid("Transpose"));

        assertFalse(Operation.isValid("jfd"));
        assertFalse(Operation.isValid("lizard"));
        assertFalse(Operation.isValid("Boop1i"));
    }
}