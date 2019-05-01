package CodeGeneration.DataFlow.Operations;

public class Addition extends BinaryOperation {
    @Override
    float operation(float a, float b) {
        return a + b;
    }
}
