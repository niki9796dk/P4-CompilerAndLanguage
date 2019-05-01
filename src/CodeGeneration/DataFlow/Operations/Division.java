package CodeGeneration.DataFlow.Operations;

public class Division extends BinaryOperation {
    @Override
    float operation(float a, float b) {
        return a / b;
    }
}
