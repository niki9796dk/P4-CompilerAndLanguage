package CodeGeneration.DataFlow.Operations;

public class Multiplication extends BinaryOperation {
    @Override
    float operation(float a, float b) {
        return a * b;
    }
}
