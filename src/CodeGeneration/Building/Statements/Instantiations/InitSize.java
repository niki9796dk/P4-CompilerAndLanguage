package CodeGeneration.Building.Statements.Instantiations;

import CodeGeneration.Building.Statement;

public class InitSize implements Statement {
    private int valueA;
    private int valueB;

    public InitSize(int valueA, int valueB) {
        this.valueA = valueA;
        this.valueB = valueB;
    }

    @Override
    public String toString() {
        return "(new Pair<Integer, Integer>(" + this.valueA + ", " + this.valueB + "))";
    }
}
