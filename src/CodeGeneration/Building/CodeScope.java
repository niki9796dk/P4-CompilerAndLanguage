package CodeGeneration.Building;

public interface CodeScope extends ParameterCollection, StatementCollection {
    String getId();

    void setId(String id);

    ParameterCollection getParameters();

    StatementCollection getStatements();
}
