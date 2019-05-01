package CodeGeneration.DataFlow.Operations;

public class Subtraction extends BinaryOperation {
    @Override
    float operation(float a, float b) {
        return a - b;
    }
}
