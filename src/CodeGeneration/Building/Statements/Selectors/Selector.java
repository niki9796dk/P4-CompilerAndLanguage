package CodeGeneration.Building.Statements.Selectors;

import CodeGeneration.Building.Statement;

public class Selector implements Statement {

    // Field:
    private String id;

    // Constructor:
    public Selector(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return this.id;
    }
}
