package SymbolTableImplementation.Enums;

import java.util.HashMap;

/**
 * An operation. Used to keep track of operations through Enum functionality, as well as separate the keyword string from the internal datastructure.
 */
public enum Operation {
    ADDITION("Addition"),
    MULTIPLICATION("Multiplication"),
    SUBTRACTION("Subtraction"),
    ELEMENTWISE_ADDITION("_Addition"),
    ELEMENTWISE_MULTIPLICATION("_Multiplication"),
    ELEMENTWISE_SUBTRACTION("_Subtraction"),
    ELEMENTWISE_DIVISION("_Division"),
    SIGMOID("_Sigmoid"),
    TANH("_Tanh"),
    RELU("_Relu"),
    TRANSPOSE("Transpose");

    private static HashMap<String, Operation> operations = Operation.createMap();

    private String keyword;

    Operation(String keyword) {
        this.keyword = keyword;
    }

    private static HashMap<String, Operation> createMap(){
        HashMap<String, Operation> values = new HashMap<>();
        for(Operation operation: Operation.values()){
            values.put(operation.keyword, operation);
        }
        return values;
    }


    /**
     * @param keyword A string which represents an operations keyword.
     * @return True if the operation exists within the list of Enums, and otherwise it will return false. This method is case sensitive.
     */
    public static boolean isValid(String keyword){
        return Operation.operations.containsKey(keyword);
    }
}
