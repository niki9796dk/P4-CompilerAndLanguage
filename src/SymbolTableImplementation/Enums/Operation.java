package SymbolTableImplementation.Enums;

import java.util.HashMap;

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

    String keyword;

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

    public static boolean isValid(String keyword){
        return Operation.operations.containsKey(keyword);
    }

    /*
    *
            // Matrix arithmetic operations
            "Addition", "Multiplication", "Subtraction",
            // Unitwise Arithmetic operations
            "_Addition", "_Multiplication", "_Subtraction", "_Division",
            // Activation functions
            "_Sigmoid", "_Tanh", "_Relu",
            // Matrix operations
            "Transpose"));
    * */
}
