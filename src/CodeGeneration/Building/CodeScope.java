package CodeGeneration.Building;

import AST.Enums.NodeEnum;
import DataStructures.Pair;

import java.util.List;

public interface CodeScope extends ParameterCollection, StatementCollection {
    String getId();
    void setId(String id);

    ParameterCollection getParameters();
    StatementCollection getStatements();
}
