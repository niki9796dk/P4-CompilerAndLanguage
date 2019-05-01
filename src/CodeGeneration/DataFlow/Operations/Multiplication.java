package CodeGeneration.DataFlow.Operations;

public class Multiplication extends BinaryOperation {
    @Override
    float operation(float in1, float in2) {
        return in1 * in2;
    }
}
