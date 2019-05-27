package SymbolTableImplementation.Enums;

import java.util.HashMap;

/**
 * An operation. Used to keep track of operations through Enum functionality, as well as separate the keyword string from the internal data structure.
 */
public enum Operation {
    @SuppressWarnings("unused")
    ADDITION("_Addition"),
    MULTIPLICATION("Multiplication"),
    SUBTRACTION("_Subtraction"),
    ELEMENTWISE_ADDITION("_Addition"),
    ELEMENTWISE_MULTIPLICATION("_Multiplication"),
    ELEMENTWISE_SUBTRACTION("_Subtraction"),
    ELEMENTWISE_DIVISION("_Division"),
    SIGMOID("_Sigmoid"),
    TANH("_Tanh"),
    RELU("_Relu"),
    TRANSPOSE("Transpose");

    /**
     * The keyword of the operation as a string.
     */
    private String keyword;

    /**
     * A map containing all the operation enums. Useful for quickly and efficiently finding an operation using the word.
     * Used in the method {@link #isValid(String)}.
     * Key: Keyword of the operation as a string.
     * Value: The operation with the specified keyword.
     */
    private static final HashMap<String, Operation> operations = Operation.createMap();

    /**
     * Construct a new operation with a specific single keyword string.
     *
     * @param keyword The keyword of the operation.
     */
    Operation(String keyword) {
        this.keyword = keyword;
    }

    /**
     * @return A map containing all operations as entries, with the value being the keyword of the operation.
     */
    private static HashMap<String, Operation> createMap() {
        HashMap<String, Operation> values = new HashMap<>();
        for (Operation operation : Operation.values()) {
            values.put(operation.keyword, operation);
        }
        return values;
    }

    /**
     * @param keyword A string which represents an operations keyword.
     * @return True if the operation exists within the list of Enums, and otherwise it will return false. This method is case sensitive.
     */
    public static boolean isValid(String keyword) {
        return Operation.operations.containsKey(keyword);
    }
}
