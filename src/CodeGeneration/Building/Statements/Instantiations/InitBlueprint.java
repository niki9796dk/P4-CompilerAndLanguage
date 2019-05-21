package CodeGeneration.Building.Statements.Instantiations;

import CodeGeneration.Building.Statement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InitBlueprint implements Statement {
    private String buildId;

    public InitBlueprint(String buildId) {
        this.buildId = buildId;
    }

    @Override
    public String toString() {
        return this.buildId + ".class";
    }
}
