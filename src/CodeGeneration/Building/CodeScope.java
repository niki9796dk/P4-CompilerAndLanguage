package CodeGeneration.Building;

import AST.Enums.NodeEnum;
import DataStructures.Pair;

import java.util.List;

public interface CodeScope {
    String getId();
    List<Statement> getStatements();
    List<Pair<NodeEnum, String>> getParameters();

    void setId(String id);
    void addStatement(Statement statement);
    void addParameter(Pair<NodeEnum, String> parameter);
}
