package CodeGeneration.DataFlow.Operations;

public class Subtraction extends BinaryOperation {
    @Override
    float operation(float in1, float in2) {
        return in1 - in2;
    }
}
