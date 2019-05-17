package CodeGeneration.Building.Statements.Selectors;

import CodeGeneration.Building.Statement;

public class Selector implements Statement {
    private String id;

    public Selector(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return this.id;
    }
}
