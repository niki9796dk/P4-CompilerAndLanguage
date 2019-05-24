package CodeGeneration.Building.Statements.Instantiations;

import CodeGeneration.Building.Statement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InitBlueprint implements Statement {

    // Fields:
    private String buildId;

    // Constructor:
    public InitBlueprint(String buildId) {
        this.buildId = buildId;
    }

    @Override
    public String toString() {
        return this.buildId + ".class";
    }
}
